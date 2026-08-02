package com.example.bron.booking;

import com.example.bron.auth.security.CurrentUserService;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.booking.dto.CancelBookingRequestDto;
import com.example.bron.enums.BookingStatus;
import com.example.bron.enums.MatchStatus;
import com.example.bron.exception.BadRequestException;
import com.example.bron.exception.ConflictException;
import com.example.bron.exception.ForbiddenException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.match.MatchRepository;
import com.example.bron.notification.enums.NotificationTemplate;
import com.example.bron.notification.event.BookingEvent;
import com.example.bron.stadium.StadiumRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final StadiumRepository stadiumRepository;
  private final MatchRepository matchRepository;
  private final BookingMapper bookingMapper;
  private final ApplicationEventPublisher eventPublisher;
  private final CurrentUserService currentUserService;

  private static final long MIN_CANCEL_HOURS_BEFORE_START = 1;

  @Transactional
  @Override
  public BookingResponseDto createBooking(BookingRequestDto requestDto) {
    if (!requestDto.getEndTime().isAfter(requestDto.getStartTime())) {
      throw new BadRequestException("BOOKING_INVALID_TIME_RANGE");
    }

    if (requestDto.getStartTime().isBefore(LocalDateTime.now())) {
      throw new BadRequestException("BOOKING_TIME_IN_PAST");
    }

    UserEntity user = null;
    if (requestDto.getUserId() != null) {
      user = userRepository.findById(requestDto.getUserId()).orElseThrow(() ->
          new NotFoundException("booking_user_not_fount",
              List.of(requestDto.getUserId().toString()))
      );
    }
    var stadium = stadiumRepository.findByIdForUpdate(requestDto.getStadiumId()).orElseThrow(() ->
        new NotFoundException("booking_stadium_not_found",
            List.of(requestDto.getStadiumId().toString()))
    );

    if (stadium.getOwner() == null) {
      throw new BadRequestException("STADIUM_HAS_NO_OWNER_OFFLINE_BOOKING_ONLY");
    }

    boolean exists = bookingRepository.existsOverlappingBooking(
        requestDto.getStadiumId(),
        requestDto.getStartTime(),
        requestDto.getEndTime()
    );

    if (exists) {
      throw new ConflictException("BOOKING_TIME_ALREADY_TAKEN", List.of("exists"));
    }

    var entity = bookingMapper.toEntity(requestDto);

    if (requestDto.getMatchId() != null) {
      var match = matchRepository.findById(requestDto.getMatchId()).orElseThrow(() ->
          new NotFoundException("booking_match_not_found",
              List.of(requestDto.getMatchId().toString()))
      );
      entity.setMatch(match);
    } else {
      entity.setMatch(null);
    }

    boolean createdByAdmin = currentUserService.isSuperAdmin()
        || currentUserService.isDistrictAdmin();

    entity.setCreatedAt(LocalDateTime.now());
    entity.setStadium(stadium);
    entity.setUser(user);
    entity.setStatus(createdByAdmin ? BookingStatus.CONFIRMED : BookingStatus.PENDING);

    var saved = bookingRepository.save(entity);

    if (saved.getStatus() == BookingStatus.CONFIRMED) {
      if (user != null) {
        eventPublisher.publishEvent(new BookingEvent(
            user.getId(),
            NotificationTemplate.BOOKING_CONFIRMED,
            stadium.getName(), saved.getStartTime().toString()
        ));
      }
    } else {
      if (user != null) {
        eventPublisher.publishEvent(new BookingEvent(
            user.getId(),
            NotificationTemplate.BOOKING_PENDING,
            stadium.getName(), saved.getStartTime().toString()
        ));
      }
      eventPublisher.publishEvent(new BookingEvent(
          stadium.getOwner().getId(),
          NotificationTemplate.BOOKING_NEW_REQUEST,
          stadium.getName(), saved.getStartTime().toString()
      ));
    }

    return bookingMapper.toDto(saved);
  }

  @Transactional
  @Override
  public BookingResponseDto updateBooking(Long id, BookingRequestDto dto) {
    var booking = bookingEntity(id);

    var currentUser = currentUserService.getCurrentUser();
    boolean isAdmin = currentUserService.isSuperAdmin()
        || currentUserService.isDistrictAdmin();
    boolean isOwner = booking.getUser() != null
        && booking.getUser().getId().equals(currentUser.getId());
    if (!isAdmin && !isOwner) {
      throw new ForbiddenException("BOOKING_UPDATE_NOT_ALLOWED");
    }

    if (booking.getStatus() == BookingStatus.CANCELLED
        || booking.getStatus() == BookingStatus.REJECTED) {
      throw new BadRequestException("BOOKING_NOT_EDITABLE");
    }

    var now = LocalDateTime.now();
    if (!booking.getStartTime().isAfter(now)) {
      throw new BadRequestException("BOOKING_ALREADY_STARTED");
    }

    var stadium = booking.getStadium();
    boolean stadiumChanged = dto.getStadiumId() != null
        && !dto.getStadiumId().equals(stadium.getId());
    if (stadiumChanged) {
      stadium = stadiumRepository.findByIdForUpdate(dto.getStadiumId()).orElseThrow(() ->
          new NotFoundException("booking_stadium_not_found",
              List.of(dto.getStadiumId().toString())));
      if (stadium.getOwner() == null) {
        throw new BadRequestException("STADIUM_HAS_NO_OWNER_OFFLINE_BOOKING_ONLY");
      }
      booking.setStadium(stadium);
    }

    var newStart = dto.getStartTime() != null ? dto.getStartTime() : booking.getStartTime();
    var newEnd = dto.getEndTime() != null ? dto.getEndTime() : booking.getEndTime();
    boolean timeChanged = dto.getStartTime() != null || dto.getEndTime() != null;

    if (timeChanged || stadiumChanged) {
      if (!newEnd.isAfter(newStart)) {
        throw new BadRequestException("BOOKING_INVALID_TIME_RANGE");
      }
      if (newStart.isBefore(now)) {
        throw new BadRequestException("BOOKING_TIME_IN_PAST");
      }
      boolean conflict = bookingRepository
          .findConflictingBookings(stadium.getId(), newStart, newEnd)
          .stream()
          .anyMatch(b -> !b.getId().equals(booking.getId()));
      if (conflict) {
        throw new ConflictException("BOOKING_TIME_ALREADY_TAKEN", List.of("exists"));
      }
      booking.setStartTime(newStart);
      booking.setEndTime(newEnd);
    }

    if (dto.getMatchId() != null) {
      var match = matchRepository.findById(dto.getMatchId()).orElseThrow(() ->
          new NotFoundException("booking_match_not_found",
              List.of(dto.getMatchId().toString())));
      booking.setMatch(match);
    }

    bookingMapper.updateEntity(booking, dto);

    // Oddiy foydalanuvchi tasdiqlangan bronning vaqti/stadionini o'zgartirsa,
    // egasi qayta tasdiqlashi uchun PENDING holatga qaytadi.
    boolean needsReapproval = !isAdmin
        && booking.getStatus() == BookingStatus.CONFIRMED
        && (timeChanged || stadiumChanged);
    if (needsReapproval) {
      booking.setStatus(BookingStatus.PENDING);
      booking.setConfirmedBy(null);
      booking.setConfirmedAt(null);
    }

    var saved = bookingRepository.save(booking);

    if (needsReapproval && stadium.getOwner() != null) {
      eventPublisher.publishEvent(new BookingEvent(
          stadium.getOwner().getId(),
          NotificationTemplate.BOOKING_NEW_REQUEST,
          stadium.getName(), saved.getStartTime().toString()
      ));
    }

    return bookingMapper.toDto(saved);
  }

  @Transactional
  @Override
  public BookingResponseDto cancelBooking(Long id, CancelBookingRequestDto dto) {
    var booking = bookingEntity(id);

    if (booking.getStatus() == BookingStatus.CANCELLED) {
      throw new ConflictException("BOOKING_ALREADY_CANCELLED", List.of(id.toString()));
    }

    var currentUser = currentUserService.getCurrentUser();
    boolean isAdmin = currentUserService.isSuperAdmin()
        || currentUserService.isDistrictAdmin();
    boolean isOwner = booking.getUser() != null
        && booking.getUser().getId().equals(currentUser.getId());

    if (!isAdmin && !isOwner) {
      throw new ForbiddenException("BOOKING_CANCEL_NOT_ALLOWED");
    }

    var now = LocalDateTime.now();
    if (!booking.getStartTime().isAfter(now)) {
      throw new BadRequestException("BOOKING_ALREADY_STARTED");
    }

    long hoursLeft = Duration.between(now, booking.getStartTime()).toHours();
    if (hoursLeft < MIN_CANCEL_HOURS_BEFORE_START) {
      throw new BadRequestException("BOOKING_CANCEL_TOO_LATE",
          List.of(String.valueOf(MIN_CANCEL_HOURS_BEFORE_START)));
    }

    booking.setStatus(BookingStatus.CANCELLED);
    booking.setCancelReason(dto.getReason());
    booking.setCancelledAt(now);
    booking.setCancelledBy(currentUser);

    var saved = bookingRepository.save(booking);

    var match = booking.getMatch();
    if (match != null && match.getStatus() != MatchStatus.CANCELLED) {
      match.setStatus(MatchStatus.CANCELLED);
      matchRepository.save(match);
    }

    eventPublisher.publishEvent(new BookingEvent(
        booking.getUser().getId(),
        NotificationTemplate.BOOKING_CANCELLED,
        booking.getStadium().getName()
    ));

    return bookingMapper.toDto(saved);
  }

  @Transactional
  @Override
  public BookingResponseDto confirmBooking(Long id) {
    var booking = bookingEntity(id);

    var stadium = booking.getStadium();
    currentUserService.requireOwnerOrDistrictScope(
        stadium.getOwner() == null ? null : stadium.getOwner().getId(),
        stadium.getDistrict() == null ? null : stadium.getDistrict().getId());

    if (booking.getStatus() == BookingStatus.CONFIRMED) {
      throw new ConflictException("BOOKING_ALREADY_CONFIRMED", List.of(id.toString()));
    }
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("BOOKING_NOT_PENDING");
    }

    booking.setStatus(BookingStatus.CONFIRMED);
    booking.setConfirmedBy(currentUserService.getCurrentUser());
    booking.setConfirmedAt(LocalDateTime.now());

    var saved = bookingRepository.save(booking);

    if (booking.getUser() != null) {
      eventPublisher.publishEvent(new BookingEvent(
          booking.getUser().getId(),
          NotificationTemplate.BOOKING_CONFIRMED,
          stadium.getName(), saved.getStartTime().toString()
      ));
    }

    return bookingMapper.toDto(saved);
  }

  @Transactional
  @Override
  public BookingResponseDto rejectBooking(Long id, CancelBookingRequestDto dto) {
    var booking = bookingEntity(id);

    var stadium = booking.getStadium();
    currentUserService.requireOwnerOrDistrictScope(
        stadium.getOwner() == null ? null : stadium.getOwner().getId(),
        stadium.getDistrict() == null ? null : stadium.getDistrict().getId());

    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("BOOKING_NOT_PENDING");
    }

    var now = LocalDateTime.now();
    booking.setStatus(BookingStatus.REJECTED);
    booking.setCancelReason(dto.getReason());
    booking.setCancelledAt(now);
    booking.setCancelledBy(currentUserService.getCurrentUser());

    var saved = bookingRepository.save(booking);

    var match = booking.getMatch();
    if (match != null && match.getStatus() != MatchStatus.CANCELLED) {
      match.setStatus(MatchStatus.CANCELLED);
      matchRepository.save(match);
    }

    if (booking.getUser() != null) {
      eventPublisher.publishEvent(new BookingEvent(
          booking.getUser().getId(),
          NotificationTemplate.BOOKING_REJECTED,
          stadium.getName()
      ));
    }

    return bookingMapper.toDto(saved);
  }

  @Override
  public List<BookingResponseDto> getBooking(Long id, LocalDate date) {
    var bookings = bookingRepository.findIdAndDateBookings(id, date);
    return bookings.stream()
        .map(bookingMapper::toDto)
        .toList();
  }

  @Override
  public List<BookingResponseDto> getBookings(BookingFilterParams filterParams) {
    return bookingRepository.getAll(filterParams).stream()
        .map(bookingMapper::toDto)
        .toList();
  }

  private BookingEntity bookingEntity(Long id){
    return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("booking_not_fount",List.of(id.toString())));
  }
}

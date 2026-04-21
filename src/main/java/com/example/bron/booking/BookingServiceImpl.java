package com.example.bron.booking;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.exception.BadRequestException;
import com.example.bron.exception.ConflictException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.match.MatchRepository;
import com.example.bron.notification.enums.NotificationTemplate;
import com.example.bron.notification.event.BookingEvent;
import com.example.bron.stadium.StadiumRepository;
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

  @Transactional
  @Override
  public BookingResponseDto createBooking(BookingRequestDto requestDto) {
    if (!requestDto.getEndTime().isAfter(requestDto.getStartTime())) {
      throw new BadRequestException("BOOKING_INVALID_TIME_RANGE");
    }

    if (requestDto.getStartTime().isBefore(LocalDateTime.now())) {
      throw new BadRequestException("BOOKING_TIME_IN_PAST");
    }

    var user = userRepository.findById(requestDto.getUserId()).orElseThrow(() ->
        new NotFoundException("booking_user_not_fount",
            List.of(requestDto.getUserId().toString()))
    );

    var stadium = stadiumRepository.findByIdForUpdate(requestDto.getStadiumId()).orElseThrow(() ->
        new NotFoundException("booking_stadium_not_found",
            List.of(requestDto.getStadiumId().toString()))
    );

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

    entity.setCreatedAt(LocalDateTime.now());
    entity.setStadium(stadium);
    entity.setUser(user);

    var saved = bookingRepository.save(entity);

    eventPublisher.publishEvent(new BookingEvent(
        user.getId(),
        NotificationTemplate.BOOKING_CONFIRMED,
        stadium.getName(), saved.getStartTime().toString()
    ));

    return bookingMapper.toDto(saved);
  }

  @Override
  public BookingResponseDto updateBooking(Long id, BookingRequestDto bookingRequestDto) {
//    var entity = bookingRepository.findById(id);
    return null;
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
    return bookingRepository.getAll(filterParams);
  }

  private BookingEntity bookingEntity(Long id){
    return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("booking_not_fount",List.of(id.toString())));
  }
}

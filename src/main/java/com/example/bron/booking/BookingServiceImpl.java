package com.example.bron.booking;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.exception.NotFoundException;
import com.example.bron.match.MatchRepository;
import com.example.bron.stadium.StadiumRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final StadiumRepository stadiumRepository;
  private final MatchRepository matchRepository;
  private final BookingMapper bookingMapper;

  @Override
  public BookingResponseDto createBooking(BookingRequestDto requestDto) {
    var entity = bookingMapper.toEntity(requestDto);
    var user = userRepository.findById(requestDto.getUserId()).orElseThrow(() ->
        new NotFoundException("booking_user_not_fount",List.of(requestDto.getUserId().toString())));
    var stadium = stadiumRepository.findById(requestDto.getStadiumId()).orElseThrow(() ->
        new NotFoundException("booking_stadium_not_found",List.of(requestDto.getStadiumId().toString())));
    if (requestDto.getMatchId() != null) {
      var match = matchRepository.findById(requestDto.getMatchId()).orElseThrow(() ->
          new NotFoundException("booking_match_not_found",List.of(requestDto.getMatchId().toString())));
      entity.setMatch(match);
    } else {
      entity.setMatch(null);
    }

    entity.setCreatedAt(LocalDateTime.now());
    entity.setStadium(stadium);
    entity.setUser(user);
    var saved = bookingRepository.save(entity);
    return bookingMapper.toDto(saved);
  }

  @Override
  public BookingResponseDto updateBooking(Long id, BookingRequestDto bookingRequestDto) {
//    var entity = bookingRepository.findById(id);
    return null;
  }

  @Override
  public BookingResponseDto getBooking(Long id) {
    var booking = bookingEntity(id);
    return bookingMapper.toDto(booking);
  }

  @Override
  public List<BookingResponseDto> getBookings() {
    var bookings = bookingRepository.findAll();
    return bookings.stream()
        .map(bookingMapper::toDto)
        .toList();
  }

  private BookingEntity bookingEntity(Long id){
    return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("booking_not_fount",List.of(id.toString())));
  }
}

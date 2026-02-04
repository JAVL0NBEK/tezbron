package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import java.util.List;

public interface BookingService {
  BookingResponseDto createBooking(BookingRequestDto bookingRequestDto);
  BookingResponseDto updateBooking(Long id, BookingRequestDto bookingRequestDto);
  BookingResponseDto getBooking(Long id);
  List<BookingResponseDto> getBookings();

}

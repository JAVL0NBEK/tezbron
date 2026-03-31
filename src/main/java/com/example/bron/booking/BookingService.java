package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {
  BookingResponseDto createBooking(BookingRequestDto bookingRequestDto);
  BookingResponseDto updateBooking(Long id, BookingRequestDto bookingRequestDto);
  List<BookingResponseDto> getBooking(Long id, LocalDate date);
  List<BookingResponseDto> getBookings();

}

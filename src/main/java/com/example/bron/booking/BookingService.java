package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.booking.dto.CancelBookingRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {
  BookingResponseDto createBooking(BookingRequestDto bookingRequestDto);
  BookingResponseDto updateBooking(Long id, BookingRequestDto bookingRequestDto);
  BookingResponseDto cancelBooking(Long id, CancelBookingRequestDto dto);
  List<BookingResponseDto> getBooking(Long id, LocalDate date);
  List<BookingResponseDto> getBookings(BookingFilterParams filterParams);

}

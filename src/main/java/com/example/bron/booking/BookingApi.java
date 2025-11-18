package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.common.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/booking")
@Tag(name = "Booking Management APIs", description = "Endpoints for managing Booking")
public interface BookingApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<BookingResponseDto>> createBooking(@RequestBody BookingRequestDto bookingRequest);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<BookingResponseDto>> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDto bookingRequest);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<BookingResponseDto>> getBooking(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<BookingResponseDto>>> getBookings();

}

package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.common.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController implements BookingApi {
  private final BookingService bookingService;

  @Override
  public ResponseEntity<BaseResponse<BookingResponseDto>> createBooking(
      BookingRequestDto bookingRequest) {
    var bookingResponse = bookingService.createBooking(bookingRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(bookingResponse));
  }

  @Override
  public ResponseEntity<BaseResponse<BookingResponseDto>> updateBooking(Long id,
      BookingRequestDto bookingRequest) {
    var bookingResponse = bookingService.updateBooking(id, bookingRequest);
    return ResponseEntity.ok(BaseResponse.ok(bookingResponse));
  }

  @Override
  public ResponseEntity<BaseResponse<BookingResponseDto>> getBooking(Long id) {
    var bookingResponse = bookingService.getBooking(id);
    return ResponseEntity.ok(BaseResponse.ok(bookingResponse));
  }

  @Override
  public ResponseEntity<BaseResponse<List<BookingResponseDto>>> getBookings() {
    var bookings = bookingService.getBookings();
    return ResponseEntity.ok(BaseResponse.ok(bookings));
  }
}

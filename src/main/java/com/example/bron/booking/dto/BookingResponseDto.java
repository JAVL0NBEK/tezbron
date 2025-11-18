package com.example.bron.booking.dto;

import com.example.bron.enums.BookingStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
  private Long id;
  private Long userId;
  private Long stadiumId;
  private Long matchId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Double totalPrice;
  private BookingStatus status;
  private String paymentMethod;
}

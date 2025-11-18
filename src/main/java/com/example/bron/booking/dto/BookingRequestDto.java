package com.example.bron.booking.dto;

import com.example.bron.enums.BookingStatus;
import com.example.bron.match.MatchEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

  private Long userId;
  private Long stadiumId;
  private Long matchId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Double totalPrice;
  private BookingStatus status;
  private String paymentMethod;
}

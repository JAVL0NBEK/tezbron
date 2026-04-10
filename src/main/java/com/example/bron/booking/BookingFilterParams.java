package com.example.bron.booking;

import com.example.bron.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookingFilterParams {
  private Long userId;
  private Long stadiumId;
  private Long matchId;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateFrom;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateTo;
  private Double totalPrice;
  private BookingStatus status;
  private String paymentMethod;

  @Schema(hidden = true)
  public Boolean getStartDateFromIsNull() {
    return this.startDateFrom == null;
  }

  @Schema(hidden = true)
  public Boolean getStartDateToIsNull() {
    return this.startDateTo == null;
  }

  @Schema(hidden = true)
  public Boolean getStatusIsNull() {
    return this.status == null;
  }
}

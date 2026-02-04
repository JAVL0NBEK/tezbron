package com.example.bron.match;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class MatchFilterParams {
  private Long regionId;
  private Long districtId;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateFrom;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateTo;

  @Schema(hidden = true)
  public Boolean getStartDateToIsNull() {
    return this.startDateTo == null;
  }

  @Schema(hidden = true)
  public Boolean getStartDateFromIsNull() {
    return this.startDateFrom == null;
  }
}

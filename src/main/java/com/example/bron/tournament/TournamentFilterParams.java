package com.example.bron.tournament;

import com.example.bron.enums.SportType;
import com.example.bron.enums.TournamentStatus;
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
public class TournamentFilterParams {
  private String name;
  private Long organizerId;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateFrom;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDateTo;
  private SportType sportType;
  private Integer maxTeams;
  private Long maxEntryFee;
  private TournamentStatus status;
  private String address;

  @Schema(hidden = true)
  public Boolean getStartDateFromIsNull() {
    return this.startDateFrom == null;
  }

  @Schema(hidden = true)
  public Boolean getStartDateToIsNull() {
    return this.startDateTo == null;
  }

  @Schema(hidden = true)
  public Boolean getSportTypeIsNull() {
    return this.sportType == null;
  }

  @Schema(hidden = true)
  public Boolean getStatusIsNull() {
    return this.status == null;
  }
}

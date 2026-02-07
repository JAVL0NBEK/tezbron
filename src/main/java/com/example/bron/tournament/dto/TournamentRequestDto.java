package com.example.bron.tournament.dto;

import com.example.bron.enums.SportType;
import com.example.bron.enums.TournamentStatus;
import com.example.bron.stadium.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentRequestDto {

  private String name;

  private Long organizerId;

  private LocalDate startDate;
  private LocalDate endDate;

  private SportType sportType;

  private Integer maxTeams;
  private Integer teamApplied;
  private Double entryFee;

  private String rules;

  private TournamentStatus status;
  @Schema(type = "string", example = "18:30")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime startTime;

  @Schema(type = "string", example = "18:30")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime endTime;
  private LocationDto location;
  private String address;
  private String prizes;

}

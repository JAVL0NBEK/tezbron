package com.example.bron.tournament.dto;

import com.example.bron.enums.SportType;
import com.example.bron.enums.TournamentStatus;
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
public class TournamentResponseDto {
  private Long id;

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
  private LocalTime startTime;
  private LocalTime endTime;
  private Object location;
  private String address;
  private String prizes;

}

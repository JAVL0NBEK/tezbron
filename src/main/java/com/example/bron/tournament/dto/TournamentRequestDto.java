package com.example.bron.tournament.dto;

import com.example.bron.enums.TournamentStatus;
import java.time.LocalDate;
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

  private String sportType;

  private Integer maxTeams;
  private Double entryFee;

  private String rules;

  private TournamentStatus status;

}

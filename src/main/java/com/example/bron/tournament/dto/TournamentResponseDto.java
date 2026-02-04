package com.example.bron.tournament.dto;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.enums.TournamentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
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

  private String sportType;

  private Integer maxTeams;
  private Double entryFee;

  private String rules;

  private TournamentStatus status;

}

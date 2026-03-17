package com.example.bron.tournament;

import com.example.bron.enums.BookingStatus;
import com.example.bron.team.TeamEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "tournament_teams",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"tournament_id", "team_id"}
    )
)
@Getter
@Setter
public class TournamentTeamEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tournament_id", nullable = false)
  private TournamentEntity tournament;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private TeamEntity team;

  private LocalDateTime registeredAt;

  @Enumerated(EnumType.STRING)
  private BookingStatus status;
  // PENDING, APPROVED, REJECTED

}

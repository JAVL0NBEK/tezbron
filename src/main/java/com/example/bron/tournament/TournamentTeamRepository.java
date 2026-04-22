package com.example.bron.tournament;

import com.example.bron.team.TeamEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TournamentTeamRepository extends JpaRepository<TournamentTeamEntity, Long> {

  long countByTournamentId(Long tournamentId);
  boolean existsByTournamentIdAndTeamId(Long tournamentId, Long teamId);

  @Query("""
      select distinct t
      from TournamentTeamEntity tt
      join tt.team t
      left join fetch t.members m
      left join fetch m.user
      where tt.tournament.id = :tournamentId
      """)
  List<TeamEntity> getTeamJoinedTour(@Param("tournamentId") Long tournamentId);
}
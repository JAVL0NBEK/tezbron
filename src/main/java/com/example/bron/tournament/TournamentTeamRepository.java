package com.example.bron.tournament;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentTeamRepository extends JpaRepository<TournamentTeamEntity, Long> {

  long countByTournamentId(Long tournamentId);
  boolean existsByTournamentIdAndTeamId(Long tournamentId, Long teamId);

}

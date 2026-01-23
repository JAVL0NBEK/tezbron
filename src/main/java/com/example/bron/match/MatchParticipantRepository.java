package com.example.bron.match;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchParticipantRepository extends JpaRepository<MatchParticipantEntity, Long> {
  boolean existsByMatchIdAndUserId(Long matchId, Long userId);

  Optional<MatchParticipantEntity> findByMatchIdAndUserId(Long matchId, Long userId);

  long countByMatchId(Long matchId);

}

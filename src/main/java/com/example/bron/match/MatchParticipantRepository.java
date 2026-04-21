package com.example.bron.match;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchParticipantRepository extends JpaRepository<MatchParticipantEntity, Long> {
  boolean existsByMatchIdAndUserId(Long matchId, Long userId);

  Optional<MatchParticipantEntity> findByMatchIdAndUserId(Long matchId, Long userId);

  long countByMatchId(Long matchId);

  @Query("""
      select p.match.id as matchId, p.user.id as userId
      from MatchParticipantEntity p
      where p.match.id in :matchIds
      """)
  List<MatchParticipantProjection> findParticipantUserIdsByMatchIds(Collection<Long> matchIds);

  interface MatchParticipantProjection {
    Long getMatchId();
    Long getUserId();
  }
}

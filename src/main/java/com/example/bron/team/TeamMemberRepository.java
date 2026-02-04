package com.example.bron.team;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Long> {
  boolean existsByTeamIdAndUserId(Long teamId, Long userId);

  Optional<TeamMemberEntity> findByTeamIdAndUserId(Long teamId, Long userId);

  long countByTeamId(Long teamId);

}

package com.example.bron.auth.security.refresh;

import com.example.bron.auth.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByToken(String token);

  @Modifying
  @Query("update RefreshTokenEntity r set r.revoked = true where r.user = :user and r.revoked = false")
  void revokeAllByUser(UserEntity user);
}

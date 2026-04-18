package com.example.bron.auth.security.refresh;

import com.example.bron.auth.security.JwtService;
import com.example.bron.auth.user.UserEntity;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository repository;
  private final JwtService jwtService;

  /** Login bo'lganda yangi refresh token yaratadi (eski tokenlarni bekor qiladi). */
  @Transactional
  public RefreshTokenEntity create(UserEntity user) {
    repository.revokeAllByUser(user);

    RefreshTokenEntity entity = new RefreshTokenEntity();
    entity.setUser(user);
    entity.setToken(UUID.randomUUID().toString() + "-" + UUID.randomUUID());
    entity.setExpiryDate(Instant.now().plusMillis(jwtService.getRefreshExpirationMs()));
    entity.setRevoked(false);
    return repository.save(entity);
  }

  /** Refresh tokenni topib, validligini tekshiradi. Noto'g'ri/expired bo'lsa exception. */
  @Transactional(readOnly = true)
  public RefreshTokenEntity verify(String token) {
    RefreshTokenEntity entity = repository.findByToken(token)
        .orElseThrow(() -> new RefreshTokenException("REFRESH_TOKEN_NOT_FOUND"));

    if (entity.isRevoked()) {
      throw new RefreshTokenException("REFRESH_TOKEN_REVOKED");
    }
    if (entity.getExpiryDate().isBefore(Instant.now())) {
      throw new RefreshTokenException("REFRESH_TOKEN_EXPIRED");
    }
    return entity;
  }

  /** Token rotation: eski refreshni revoke qiladi, yangisini chiqaradi. */
  @Transactional
  public RefreshTokenEntity rotate(RefreshTokenEntity old) {
    // Eski tokenni revoke
    old.setRevoked(true);
    repository.save(old);

    // Yangi token yaratish (create() ichidagi revokeAll eski tokenlarni tozalaydi)
    RefreshTokenEntity entity = new RefreshTokenEntity();
    entity.setUser(old.getUser());
    entity.setToken(UUID.randomUUID().toString() + "-" + UUID.randomUUID());
    entity.setExpiryDate(Instant.now().plusMillis(jwtService.getRefreshExpirationMs()));
    entity.setRevoked(false);
    return repository.save(entity);
  }

  @Transactional
  public void revokeAll(UserEntity user) {
    repository.revokeAllByUser(user);
  }
}

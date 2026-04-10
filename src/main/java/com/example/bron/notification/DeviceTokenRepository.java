package com.example.bron.notification;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity, Long> {

  List<DeviceTokenEntity> findByUserId(Long userId);

  Optional<DeviceTokenEntity> findByToken(String token);

  void deleteByToken(String token);

  @Query("select dt.token from DeviceTokenEntity dt where dt.user.id = :userId")
  List<String> findTokensByUserId(Long userId);

  @Query("select dt.token from DeviceTokenEntity dt")
  List<String> findAllTokens();

  @Query("select dt.token from DeviceTokenEntity dt where dt.user.id in :userIds")
  List<String> findTokensByUserIds(List<Long> userIds);

  boolean existsByToken(String token);
}

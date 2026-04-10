package com.example.bron.notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Long> {

  List<UserNotificationEntity> findByUserIdOrderByIdDesc(Long userId);

  List<UserNotificationEntity> findByUserIdAndIsReadFalse(Long userId);

  long countByUserIdAndIsReadFalse(Long userId);
}

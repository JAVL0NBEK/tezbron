package com.example.bron.notification;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.NotFoundException;
import com.example.bron.notification.dto.DeviceTokenRequestDto;
import com.example.bron.notification.dto.NotificationRequestDto;
import com.example.bron.notification.dto.NotificationResponseDto;
import com.example.bron.notification.enums.NotificationTarget;
import com.example.bron.notification.enums.NotificationTemplate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserNotificationRepository userNotificationRepository;
  private final DeviceTokenRepository deviceTokenRepository;
  private final UserRepository userRepository;
  private final FcmService fcmService;

  @Override
  @Transactional
  public void registerDeviceToken(DeviceTokenRequestDto dto) {
    if (deviceTokenRepository.existsByToken(dto.getToken())) {
      return;
    }
    var user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new NotFoundException("user_not_found", List.of(dto.getUserId().toString())));

    var entity = new DeviceTokenEntity();
    entity.setUser(user);
    entity.setToken(dto.getToken());
    entity.setDeviceType(dto.getDeviceType());
    entity.setCreatedAt(LocalDateTime.now());
    deviceTokenRepository.save(entity);
  }

  @Override
  @Transactional
  public void removeDeviceToken(String token) {
    deviceTokenRepository.deleteByToken(token);
  }

  @Override
  @Transactional
  public void sendToUser(NotificationRequestDto dto) {
    var user = userRepository.findById(dto.getTargetUserId())
        .orElseThrow(() -> new NotFoundException("user_not_found", List.of(dto.getTargetUserId().toString())));

    var notification = saveNotification(dto, NotificationTarget.USER);
    saveUserNotification(user.getId(), notification);
    fcmService.sendToUser(user.getId(), dto.getTitle(), dto.getBody(),
        buildDataPayload(notification), deviceTokenRepository);
  }

  @Override
  @Transactional
  public void sendToAll(NotificationRequestDto dto) {
    var notification = saveNotification(dto, NotificationTarget.ALL);

    var allUsers = userRepository.findAll();
    for (var user : allUsers) {
      saveUserNotification(user.getId(), notification);
    }
    fcmService.sendToAll(dto.getTitle(), dto.getBody(),
        buildDataPayload(notification), deviceTokenRepository);
  }

  @Async
  @Override
  @Transactional
  public void sendByTemplate(Long userId, NotificationTemplate template, Object... args) {
    String title = template.getTitleTemplate();
    String body = template.formatBody(args);

    var notification = new NotificationEntity();
    notification.setTitle(title);
    notification.setBody(body);
    notification.setType(template.getType());
    notification.setTargetType(NotificationTarget.USER);
    notification.setSentAt(LocalDateTime.now());
    notificationRepository.save(notification);

    saveUserNotification(userId, notification);
    fcmService.sendToUser(userId, title, body,
        buildDataPayload(notification), deviceTokenRepository);
  }

  @Async
  @Override
  @Transactional
  public void sendByTemplateToUsers(List<Long> userIds, NotificationTemplate template, Object... args) {
    String title = template.getTitleTemplate();
    String body = template.formatBody(args);

    var notification = new NotificationEntity();
    notification.setTitle(title);
    notification.setBody(body);
    notification.setType(template.getType());
    notification.setTargetType(NotificationTarget.USER);
    notification.setSentAt(LocalDateTime.now());
    notificationRepository.save(notification);

    for (Long userId : userIds) {
      saveUserNotification(userId, notification);
    }
    fcmService.sendToUsers(userIds, title, body,
        buildDataPayload(notification), deviceTokenRepository);
  }

  @Override
  public List<NotificationResponseDto> getMyNotifications(Long userId) {
    var userNotifications = userNotificationRepository.findByUserIdOrderByIdDesc(userId);
    return userNotifications.stream().map(un -> {
      var n = un.getNotification();
      return new NotificationResponseDto(
          un.getId(),
          n.getTitle(),
          n.getBody(),
          n.getType(),
          n.getTargetType(),
          un.getIsRead(),
          n.getSentAt(),
          un.getReadAt()
      );
    }).toList();
  }

  @Override
  public long getUnreadCount(Long userId) {
    return userNotificationRepository.countByUserIdAndIsReadFalse(userId);
  }

  @Override
  @Transactional
  public void markAsRead(Long notificationId) {
    var userNotification = userNotificationRepository.findById(notificationId)
        .orElseThrow(() -> new NotFoundException("notification_not_found", List.of(notificationId.toString())));
    userNotification.setIsRead(true);
    userNotification.setReadAt(LocalDateTime.now());
    userNotificationRepository.save(userNotification);
  }

  @Override
  @Transactional
  public void markAllAsRead(Long userId) {
    var unread = userNotificationRepository.findByUserIdAndIsReadFalse(userId);
    var now = LocalDateTime.now();
    for (var un : unread) {
      un.setIsRead(true);
      un.setReadAt(now);
    }
    userNotificationRepository.saveAll(unread);
  }

  private NotificationEntity saveNotification(NotificationRequestDto dto, NotificationTarget target) {
    var notification = new NotificationEntity();
    notification.setTitle(dto.getTitle());
    notification.setBody(dto.getBody());
    notification.setType(dto.getType());
    notification.setTargetType(target);
    notification.setSentAt(LocalDateTime.now());

    if (dto.getSenderId() != null) {
      var sender = userRepository.findById(dto.getSenderId()).orElse(null);
      notification.setSender(sender);
    }
    return notificationRepository.save(notification);
  }

  private Map<String, String> buildDataPayload(NotificationEntity notification) {
    Map<String, String> data = new HashMap<>();
    data.put("notificationId", notification.getId().toString());
    data.put("type", notification.getType().name());
    data.put("targetType", notification.getTargetType().name());
    return data;
  }

  private void saveUserNotification(Long userId, NotificationEntity notification) {
    var user = userRepository.findById(userId).orElse(null);
    if (user == null) return;

    var userNotification = new UserNotificationEntity();
    userNotification.setUser(user);
    userNotification.setNotification(notification);
    userNotification.setIsRead(false);
    userNotificationRepository.save(userNotification);
  }
}

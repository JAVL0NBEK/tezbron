package com.example.bron.notification;

import com.example.bron.notification.dto.DeviceTokenRequestDto;
import com.example.bron.notification.dto.NotificationRequestDto;
import com.example.bron.notification.dto.NotificationResponseDto;
import com.example.bron.notification.enums.NotificationTemplate;
import java.util.List;

public interface NotificationService {

  // Device token
  void registerDeviceToken(DeviceTokenRequestDto dto);
  void removeDeviceToken(String token);

  // Admin/Owner push
  void sendToUser(NotificationRequestDto dto);
  void sendToAll(NotificationRequestDto dto);

  // Template-based push (internal)
  void sendByTemplate(Long userId, NotificationTemplate template, Object... args);
  void sendByTemplateToUsers(List<Long> userIds, NotificationTemplate template, Object... args);

  // User notifications
  List<NotificationResponseDto> getMyNotifications(Long userId);
  long getUnreadCount(Long userId);
  void markAsRead(Long notificationId);
  void markAllAsRead(Long userId);
}

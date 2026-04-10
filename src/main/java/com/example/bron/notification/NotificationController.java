package com.example.bron.notification;

import com.example.bron.common.BaseResponse;
import com.example.bron.notification.dto.DeviceTokenRequestDto;
import com.example.bron.notification.dto.NotificationRequestDto;
import com.example.bron.notification.dto.NotificationResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

  private final NotificationService notificationService;

  @Override
  public ResponseEntity<BaseResponse<Void>> registerDeviceToken(DeviceTokenRequestDto dto) {
    notificationService.registerDeviceToken(dto);
    return ResponseEntity.ok(BaseResponse.noContent("Device token registered"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> removeDeviceToken(String token) {
    notificationService.removeDeviceToken(token);
    return ResponseEntity.ok(BaseResponse.noContent("Device token removed"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> sendToUser(NotificationRequestDto dto) {
    notificationService.sendToUser(dto);
    return ResponseEntity.ok(BaseResponse.noContent("Notification sent"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> sendToAll(NotificationRequestDto dto) {
    notificationService.sendToAll(dto);
    return ResponseEntity.ok(BaseResponse.noContent("Notification sent to all"));
  }

  @Override
  public ResponseEntity<BaseResponse<List<NotificationResponseDto>>> getMyNotifications(Long userId) {
    var notifications = notificationService.getMyNotifications(userId);
    return ResponseEntity.ok(BaseResponse.ok(notifications));
  }

  @Override
  public ResponseEntity<BaseResponse<Long>> getUnreadCount(Long userId) {
    var count = notificationService.getUnreadCount(userId);
    return ResponseEntity.ok(BaseResponse.ok(count));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> markAsRead(Long id) {
    notificationService.markAsRead(id);
    return ResponseEntity.ok(BaseResponse.noContent("Marked as read"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> markAllAsRead(Long userId) {
    notificationService.markAllAsRead(userId);
    return ResponseEntity.ok(BaseResponse.noContent("All marked as read"));
  }
}

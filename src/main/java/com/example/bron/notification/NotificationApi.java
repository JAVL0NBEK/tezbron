package com.example.bron.notification;

import com.example.bron.common.BaseResponse;
import com.example.bron.notification.dto.DeviceTokenRequestDto;
import com.example.bron.notification.dto.NotificationRequestDto;
import com.example.bron.notification.dto.NotificationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/notifications")
@Tag(name = "Notification Management APIs", description = "Endpoints for managing notifications and device tokens")
public interface NotificationApi {

  // Device token
  @PostMapping("/device-token/register")
  ResponseEntity<BaseResponse<Void>> registerDeviceToken(@RequestBody DeviceTokenRequestDto dto);

  @DeleteMapping("/device-token")
  ResponseEntity<BaseResponse<Void>> removeDeviceToken(@RequestParam String token);

  // Admin push
  @PostMapping("/send")
  ResponseEntity<BaseResponse<Void>> sendToUser(@RequestBody NotificationRequestDto dto);

  @PostMapping("/send-to-all")
  ResponseEntity<BaseResponse<Void>> sendToAll(@RequestBody NotificationRequestDto dto);

  // User notifications
  @GetMapping("/my/{userId}")
  ResponseEntity<BaseResponse<List<NotificationResponseDto>>> getMyNotifications(@PathVariable Long userId);

  @GetMapping("/unread-count/{userId}")
  ResponseEntity<BaseResponse<Long>> getUnreadCount(@PathVariable Long userId);

  @PutMapping("/{id}/read")
  ResponseEntity<BaseResponse<Void>> markAsRead(@PathVariable Long id);

  @PutMapping("/read-all/{userId}")
  ResponseEntity<BaseResponse<Void>> markAllAsRead(@PathVariable Long userId);
}

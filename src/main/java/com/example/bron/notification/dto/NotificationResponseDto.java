package com.example.bron.notification.dto;

import com.example.bron.notification.enums.NotificationTarget;
import com.example.bron.notification.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
  private Long id;
  private String title;
  private String body;
  private NotificationType type;
  private NotificationTarget targetType;
  private Boolean isRead;
  private LocalDateTime sentAt;
  private LocalDateTime readAt;
}

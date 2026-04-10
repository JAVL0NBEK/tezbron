package com.example.bron.notification.dto;

import com.example.bron.notification.enums.NotificationTarget;
import com.example.bron.notification.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {
  private String title;
  private String body;
  private NotificationType type;
  private NotificationTarget targetType;
  private Long targetUserId;
  private Long senderId;
}

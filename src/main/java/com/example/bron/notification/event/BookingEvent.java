package com.example.bron.notification.event;

import com.example.bron.notification.enums.NotificationTemplate;
import lombok.Getter;

@Getter
public class BookingEvent {
  private final Long userId;
  private final NotificationTemplate template;
  private final Object[] args;

  public BookingEvent(Long userId, NotificationTemplate template, Object... args) {
    this.userId = userId;
    this.template = template;
    this.args = args;
  }
}

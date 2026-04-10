package com.example.bron.notification.event;

import com.example.bron.notification.enums.NotificationTemplate;
import java.util.List;
import lombok.Getter;

@Getter
public class MatchEvent {
  private final Long userId;
  private final List<Long> userIds;
  private final NotificationTemplate template;
  private final Object[] args;

  public MatchEvent(Long userId, NotificationTemplate template, Object... args) {
    this.userId = userId;
    this.userIds = null;
    this.template = template;
    this.args = args;
  }

  public MatchEvent(List<Long> userIds, NotificationTemplate template, Object... args) {
    this.userId = null;
    this.userIds = userIds;
    this.template = template;
    this.args = args;
  }
}

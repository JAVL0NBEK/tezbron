package com.example.bron.notification.event;

import com.example.bron.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

  private final NotificationService notificationService;

  @Async
  @EventListener
  public void handleBookingEvent(BookingEvent event) {
    log.info("Booking event: {} for user {}", event.getTemplate(), event.getUserId());
    notificationService.sendByTemplate(event.getUserId(), event.getTemplate(), event.getArgs());
  }

  @Async
  @EventListener
  public void handleMatchEvent(MatchEvent event) {
    log.info("Match event: {}", event.getTemplate());
    if (event.getUserIds() != null && !event.getUserIds().isEmpty()) {
      notificationService.sendByTemplateToUsers(event.getUserIds(), event.getTemplate(), event.getArgs());
    } else if (event.getUserId() != null) {
      notificationService.sendByTemplate(event.getUserId(), event.getTemplate(), event.getArgs());
    }
  }

  @Async
  @EventListener
  public void handleTournamentEvent(TournamentEvent event) {
    log.info("Tournament event: {} for user {}", event.getTemplate(), event.getUserId());
    notificationService.sendByTemplate(event.getUserId(), event.getTemplate(), event.getArgs());
  }
}

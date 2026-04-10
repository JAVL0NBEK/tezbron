package com.example.bron.notification.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplate {

  // ===== Booking =====
  BOOKING_CONFIRMED(NotificationType.BOOKING,
      "Bron tasdiqlandi",
      "%s stadioniga %s da broningiz tasdiqlandi"),

  BOOKING_CANCELLED(NotificationType.BOOKING,
      "Bron bekor qilindi",
      "%s stadioniga broningiz bekor qilindi"),

  BOOKING_REMINDER(NotificationType.BOOKING,
      "Bron eslatmasi",
      "%s stadiondagi broningizga 1 soat qoldi"),

  // ===== Match =====
  MATCH_JOINED(NotificationType.MATCH,
      "O'yinga qo'shildingiz",
      "%s o'yiniga muvaffaqiyatli qo'shildingiz"),

  MATCH_FULL(NotificationType.MATCH,
      "O'yin to'ldi",
      "%s o'yinidagi barcha joylar band bo'ldi"),

  MATCH_REMINDER(NotificationType.MATCH,
      "O'yin eslatmasi",
      "%s o'yini boshlanishiga 2 soat qoldi"),

  MATCH_CANCELLED(NotificationType.MATCH,
      "O'yin bekor qilindi",
      "%s o'yini bekor qilindi"),

  MATCH_PLAYER_LEFT(NotificationType.MATCH,
      "O'yinchi chiqdi",
      "%s o'yinidan bir o'yinchi chiqdi, joy bo'shadi"),

  // ===== Tournament =====
  TOURNAMENT_TEAM_REGISTERED(NotificationType.TOURNAMENT,
      "Turnirga ro'yxatdan o'tildi",
      "%s turniriga jamoangiz muvaffaqiyatli qo'shildi"),

  TOURNAMENT_STARTED(NotificationType.TOURNAMENT,
      "Turnir boshlandi",
      "%s turniri boshlandi"),

  TOURNAMENT_FINISHED(NotificationType.TOURNAMENT,
      "Turnir yakunlandi",
      "%s turniri yakunlandi"),

  TOURNAMENT_REMINDER(NotificationType.TOURNAMENT,
      "Turnir eslatmasi",
      "%s turniri boshlanishiga 1 kun qoldi"),

  // ===== System / Admin =====
  SYSTEM_PROMO(NotificationType.SYSTEM,
      "Aksiya",
      "%s"),

  SYSTEM_NEWS(NotificationType.SYSTEM,
      "Yangilik",
      "%s"),

  SYSTEM_CUSTOM(NotificationType.SYSTEM,
      "%s",
      "%s");

  private final NotificationType type;
  private final String titleTemplate;
  private final String bodyTemplate;

  NotificationTemplate(NotificationType type, String titleTemplate, String bodyTemplate) {
    this.type = type;
    this.titleTemplate = titleTemplate;
    this.bodyTemplate = bodyTemplate;
  }

  public String formatTitle(Object... args) {
    if (this == SYSTEM_CUSTOM) {
      return String.format(titleTemplate, args);
    }
    return titleTemplate;
  }

  public String formatBody(Object... args) {
    return String.format(bodyTemplate, args);
  }
}

package com.example.bron.notification.scheduler;

import com.example.bron.booking.BookingRepository;
import com.example.bron.match.MatchEntity;
import com.example.bron.match.MatchRepository;
import com.example.bron.notification.NotificationService;
import com.example.bron.notification.enums.NotificationTemplate;
import com.example.bron.tournament.TournamentEntity;
import com.example.bron.tournament.TournamentRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

  private final BookingRepository bookingRepository;
  private final MatchRepository matchRepository;
  private final TournamentRepository tournamentRepository;
  private final NotificationService notificationService;

  // Har 30 daqiqada: Booking boshlanishiga 1 soat qolganlarni eslatish
  @Scheduled(cron = "0 */30 * * * *")
  public void sendBookingReminders() {
    log.info("Running booking reminder scheduler");
    var now = LocalDateTime.now();
    var oneHourLater = now.plusHours(1);

    var bookings = bookingRepository.findConflictingBookings(null, now, oneHourLater);
    // findConflictingBookings null stadiumId bilan ishlamaydi, alohida query kerak
    // Shuning uchun reminder uchun maxsus query qo'shamiz
    var upcomingBookings = bookingRepository.findUpcomingBookings(now, oneHourLater);
    for (var booking : upcomingBookings) {
      notificationService.sendByTemplate(
          booking.getUser().getId(),
          NotificationTemplate.BOOKING_REMINDER,
          booking.getStadium().getName()
      );
    }
  }

  // Har 1 soatda: Match boshlanishiga 2 soat qolganlarni eslatish
  @Scheduled(cron = "0 0 * * * *")
  public void sendMatchReminders() {
    log.info("Running match reminder scheduler");
    var now = LocalDateTime.now();
    var twoHoursLater = now.plusHours(2);

    var upcomingMatches = matchRepository.findUpcomingMatches(now, twoHoursLater);
    for (var match : upcomingMatches) {
      var participantIds = match.getParticipants().stream()
          .map(p -> p.getUser().getId())
          .toList();
      if (!participantIds.isEmpty()) {
        notificationService.sendByTemplateToUsers(
            participantIds,
            NotificationTemplate.MATCH_REMINDER,
            match.getTitle()
        );
      }
    }
  }

  // Har kuni soat 09:00 da: Ertangi turnirlarni eslatish
  @Scheduled(cron = "0 0 9 * * *")
  public void sendTournamentReminders() {
    log.info("Running tournament reminder scheduler");
    var tomorrow = LocalDate.now().plusDays(1);

    var upcomingTournaments = tournamentRepository.findByStartDate(tomorrow);
    for (var tournament : upcomingTournaments) {
      var teamUserIds = tournament.getTeams().stream()
          .flatMap(tt -> tt.getTeam().getMembers().stream())
          .map(m -> m.getUser().getId())
          .distinct()
          .toList();
      if (!teamUserIds.isEmpty()) {
        notificationService.sendByTemplateToUsers(
            teamUserIds,
            NotificationTemplate.TOURNAMENT_REMINDER,
            tournament.getName()
        );
      }
    }
  }
}

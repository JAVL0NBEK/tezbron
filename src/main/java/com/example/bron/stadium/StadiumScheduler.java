package com.example.bron.stadium;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StadiumScheduler {
  private final StadiumService stadiumService;

  @Scheduled(cron = "0 0 8 * * ?")
  public void updateEveryMorning() {

    LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent"));

    LocalDateTime openTime = LocalDateTime.of(today, LocalTime.of(8, 0));
    LocalDateTime closeTime = LocalDateTime.of(today, LocalTime.of(23, 0));
    stadiumService.updateAllOpenCloseTime(openTime, closeTime);
  }

}

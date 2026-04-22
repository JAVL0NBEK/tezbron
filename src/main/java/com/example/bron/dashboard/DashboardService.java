package com.example.bron.dashboard;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

  private final DashboardRepository dashboardRepository;

  public DashboardResponseDto getDashboard() {
    return new DashboardResponseDto(
        dashboardRepository.countTournaments(),
        dashboardRepository.countActiveStadiums(),
        dashboardRepository.countUsers(),
        dashboardRepository.sumTotalPriceByStadium()
    );
  }

  public WeeklyAnalyticsResponseDto getWeeklyAnalytics() {
    LocalDate today = LocalDate.now();
    LocalDate weekStart = today.with(DayOfWeek.MONDAY);
    LocalDate weekEnd = weekStart.plusDays(6);
    LocalDate prevWeekStart = weekStart.minusWeeks(1);

    LocalDateTime curStart = weekStart.atStartOfDay();
    LocalDateTime curEnd = weekEnd.plusDays(1).atStartOfDay();
    LocalDateTime prevStart = prevWeekStart.atStartOfDay();

    long currentBookings = dashboardRepository.countBookingsBetween(curStart, curEnd);
    long previousBookings = dashboardRepository.countBookingsBetween(prevStart, curStart);

    Double growthPercent;
    if (previousBookings == 0) {
      growthPercent = currentBookings == 0 ? 0.0 : 100.0;
    } else {
      double raw = ((double) (currentBookings - previousBookings) / previousBookings) * 100.0;
      growthPercent = Math.round(raw * 100.0) / 100.0;
    }

    Double weeklyRevenue = dashboardRepository.sumRevenueBetween(curStart, curEnd);
    List<DailyRevenueDto> daily = dashboardRepository.dailyRevenueBetween(curStart, curEnd);

    Map<LocalDate, Double> byDate = new HashMap<>();
    for (DailyRevenueDto d : daily) {
      byDate.put(d.date(), d.revenue());
    }
    List<DailyRevenueDto> filled = new ArrayList<>(7);
    for (int i = 0; i < 7; i++) {
      LocalDate day = weekStart.plusDays(i);
      filled.add(new DailyRevenueDto(day, byDate.getOrDefault(day, 0.0)));
    }

    return new WeeklyAnalyticsResponseDto(
        weekStart,
        weekEnd,
        currentBookings,
        previousBookings,
        growthPercent,
        weeklyRevenue == null ? 0.0 : weeklyRevenue,
        filled
    );
  }
}

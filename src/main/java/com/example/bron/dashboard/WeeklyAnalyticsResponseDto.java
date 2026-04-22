package com.example.bron.dashboard;

import java.time.LocalDate;
import java.util.List;

public record WeeklyAnalyticsResponseDto(
    LocalDate weekStart,
    LocalDate weekEnd,
    Long totalBookings,
    Long previousWeekBookings,
    Double bookingsGrowthPercent,
    Double weeklyRevenue,
    List<DailyRevenueDto> dailyRevenue
) {
}

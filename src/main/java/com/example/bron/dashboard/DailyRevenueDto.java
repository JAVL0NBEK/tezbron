package com.example.bron.dashboard;

import java.time.LocalDate;

public record DailyRevenueDto(LocalDate date, Double revenue) {
}

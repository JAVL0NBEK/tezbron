package com.example.bron.dashboard;

import java.util.List;

public record DashboardResponseDto(
    Long tournamentsCount,
    Long activeStadiumsCount,
    Long usersCount,
    List<StadiumRevenueDto> stadiumRevenues
) {
}

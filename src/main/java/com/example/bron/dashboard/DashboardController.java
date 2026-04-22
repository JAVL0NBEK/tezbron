package com.example.bron.dashboard;

import com.example.bron.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardApi {

  private final DashboardService dashboardService;

  @Override
  public ResponseEntity<BaseResponse<DashboardResponseDto>> getDashboard() {
    return ResponseEntity.ok(BaseResponse.ok(dashboardService.getDashboard()));
  }

  @Override
  public ResponseEntity<BaseResponse<WeeklyAnalyticsResponseDto>> getWeeklyAnalytics() {
    return ResponseEntity.ok(BaseResponse.ok(dashboardService.getWeeklyAnalytics()));
  }
}

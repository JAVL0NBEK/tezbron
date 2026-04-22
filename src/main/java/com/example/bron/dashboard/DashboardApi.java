package com.example.bron.dashboard;

import com.example.bron.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/admin/dashboard")
@Tag(name = "Admin Dashboard", description = "Admin dashboard uchun statistik ma'lumotlar")
public interface DashboardApi {

  @PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN')")
  @GetMapping
  @Operation(summary = "Dashboard statistikasi: stadionlar bo'yicha daromad, turnirlar, faol stadionlar va foydalanuvchilar soni")
  ResponseEntity<BaseResponse<DashboardResponseDto>> getDashboard();

  @PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN')")
  @GetMapping("/weekly")
  @Operation(summary = "Haftalik tahlil: jami bronlar, o'sish/kamayish foizi, haftalik daromad va kunlik daromad")
  ResponseEntity<BaseResponse<WeeklyAnalyticsResponseDto>> getWeeklyAnalytics();
}

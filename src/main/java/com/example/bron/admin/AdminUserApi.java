package com.example.bron.admin;

import com.example.bron.auth.user.dto.CreateStaffUserDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/admin/users")
@Tag(name = "Admin User Management", description = "SUPER_ADMIN tomonidan xodimlar (admin/owner/coach) yaratish")
public interface AdminUserApi {

  @PreAuthorize("hasRole('SUPER_ADMIN')")
  @PostMapping
  @Operation(summary = "Admin/Owner/Coach hisobini yaratish va SMS orqali parol yuborish")
  ResponseEntity<BaseResponse<UserDTO>> createStaff(@Valid @RequestBody CreateStaffUserDto dto);
}

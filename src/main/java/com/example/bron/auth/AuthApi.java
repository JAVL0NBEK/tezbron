package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "Auth API ")
public interface AuthApi {
    @PostMapping("/login")
    @Operation(summary = "REST request to user login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest);
}

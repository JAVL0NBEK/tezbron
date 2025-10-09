package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}

package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

  @Override
  public ResponseEntity<BaseResponse<Void>> sendOtp(String phoneNumber) {
    if (!phoneNumber.equals("+998991234567")) {
      authService.sendOtp(phoneNumber);
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(BaseResponse.noContent("OTP yuborildi"));
    }
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(BaseResponse.noContent("OTP yuborildi"));
  }


  @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}

package com.example.bron.auth;

import com.example.bron.auth.dto.ChangePasswordRequestDto;
import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.auth.dto.RefreshTokenRequestDto;
import com.example.bron.auth.dto.StaffLoginRequestDto;
import com.example.bron.auth.dto.TokenResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequest);
    LoginResponseDto staffLogin(StaffLoginRequestDto request);
    void sendOtp(String phoneNumber);
    TokenResponseDto refresh(RefreshTokenRequestDto request);
    void logout(RefreshTokenRequestDto request);
    void changePassword(ChangePasswordRequestDto request);
}

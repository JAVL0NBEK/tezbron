package com.example.bron.auth;

import com.example.bron.auth.dto.ChangePasswordRequestDto;
import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.auth.dto.RefreshTokenRequestDto;
import com.example.bron.auth.dto.StaffLoginRequestDto;
import com.example.bron.auth.dto.TokenResponseDto;
import com.example.bron.auth.otp.OtpService;
import com.example.bron.auth.security.CustomUserDetailsService;
import com.example.bron.auth.security.JwtService;
import com.example.bron.auth.security.refresh.RefreshTokenEntity;
import com.example.bron.auth.security.refresh.RefreshTokenService;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserMapper;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.enums.LoginStatus;
import com.example.bron.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final UserMapper userMapper;
  private final CustomUserDetailsService customUserDetailsService;
  private final OtpService otpService;
  private final RefreshTokenService refreshTokenService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginResponseDto login(LoginRequestDto dto) {

//      boolean verified = otpService.verifyOtp(dto.getPhoneNumber(), dto.getOtpCode());
//
//      if (!verified) {
//        return new LoginResponseDto(LoginStatus.INVALID_OTP);
//      }

    var optionalUser = userRepository.findByPhone("991234567");
    if (optionalUser.isEmpty()) {
      return new LoginResponseDto(LoginStatus.REGISTER_REQUIRED);
    }

    var user = optionalUser.get();
    var userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

    String accessToken = jwtService.generateAccessToken(userDetails);
    RefreshTokenEntity refresh = refreshTokenService.create(user);

    var response = userMapper.toLoginDto(user);
    response.setAccessToken(accessToken);
    response.setRefreshToken(refresh.getToken());
    response.setAccessTokenExpiresIn(jwtService.getAccessExpirationMs() / 1000);
    response.setRefreshTokenExpiresIn(jwtService.getRefreshExpirationMs() / 1000);
    return response;
  }

  @Override
  public LoginResponseDto staffLogin(StaffLoginRequestDto dto) {
    var optionalUser = userRepository.findByUsername(dto.getUsername());
    if (optionalUser.isEmpty()) {
      return new LoginResponseDto(LoginStatus.INVALID_CREDENTIALS);
    }

    UserEntity user = optionalUser.get();
    if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
      return new LoginResponseDto(LoginStatus.INVALID_CREDENTIALS);
    }

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
    String accessToken = jwtService.generateAccessToken(userDetails);
    RefreshTokenEntity refresh = refreshTokenService.create(user);

    var response = userMapper.toLoginDto(user);
    response.setAccessToken(accessToken);
    response.setRefreshToken(refresh.getToken());
    response.setAccessTokenExpiresIn(jwtService.getAccessExpirationMs() / 1000);
    response.setRefreshTokenExpiresIn(jwtService.getRefreshExpirationMs() / 1000);
    return response;
  }

  @Override
  public void sendOtp(String phoneNumber) {
    otpService.sendOtp(phoneNumber);
  }

  @Override
  public TokenResponseDto refresh(RefreshTokenRequestDto request) {
    RefreshTokenEntity old = refreshTokenService.verify(request.getRefreshToken());
    var userDetails = customUserDetailsService.loadUserByUsername(old.getUser().getUsername());

    String newAccess = jwtService.generateAccessToken(userDetails);
    RefreshTokenEntity newRefresh = refreshTokenService.rotate(old);

    return new TokenResponseDto(
        newAccess,
        newRefresh.getToken(),
        jwtService.getAccessExpirationMs() / 1000,
        jwtService.getRefreshExpirationMs() / 1000
    );
  }

  @Override
  public void logout(RefreshTokenRequestDto request) {
    RefreshTokenEntity entity = refreshTokenService.verify(request.getRefreshToken());
    refreshTokenService.revokeAll(entity.getUser());
  }

  @Override
  public void changePassword(ChangePasswordRequestDto request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserDetails principal)) {
      throw new AccessDeniedException("Not authenticated");
    }

    UserEntity user = userRepository.findByUsername(principal.getUsername())
        .orElseThrow(() -> new NotFoundException("user_not_found", List.of(principal.getUsername())));

    if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
      throw new AccessDeniedException("Eski parol noto'g'ri");
    }

    user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    refreshTokenService.revokeAll(user);
  }
}

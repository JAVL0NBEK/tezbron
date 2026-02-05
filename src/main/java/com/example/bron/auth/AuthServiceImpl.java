package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.auth.otp.OtpService;
import com.example.bron.auth.security.CustomUserDetails;
import com.example.bron.auth.security.CustomUserDetailsService;
import com.example.bron.auth.security.JwtService;
import com.example.bron.auth.user.UserMapper;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.common.BaseResponse;
import com.example.bron.enums.LoginStatus;
import com.example.bron.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final UserMapper userMapper;
  private final CustomUserDetailsService customUserDetailsService;
  private final OtpService otpService;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

//      boolean verified = otpService.verifyOtp(dto.getPhoneNumber(), dto.getOtpCode());
//
//      if (!verified) {
//        return new LoginResponseDto(LoginStatus.INVALID_OTP);
//      }

      var optionalUser = userRepository.findByPhone("991234567");
      if (optionalUser.isEmpty()){
        return new LoginResponseDto(LoginStatus.REGISTER_REQUIRED);
      }

      var user = optionalUser.get();
        var userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        var response = userMapper.toLoginDto(user);
        response.setAccessToken(token);
        return response;

    }

  @Override
  public void sendOtp(String phoneNumber) {
    otpService.sendOtp(phoneNumber);
  }

}

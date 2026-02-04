package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.auth.security.CustomUserDetails;
import com.example.bron.auth.security.CustomUserDetailsService;
import com.example.bron.auth.security.JwtService;
import com.example.bron.auth.user.UserMapper;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService customUserDetailsService;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              dto.getUsername(),
              dto.getPassword()
          )
      );

      UserDetails userDetails =
          customUserDetailsService.loadUserByUsername(dto.getUsername());
      var user = userRepository.findByUsername(dto.getUsername())
          .orElseThrow(()-> new NotFoundException("user_not_found",List.of()));
      String token = jwtService.generateToken(userDetails);
      var response = userMapper.toLoginDto(user);
      response.setAccessToken(token);
      return response;
    }
}

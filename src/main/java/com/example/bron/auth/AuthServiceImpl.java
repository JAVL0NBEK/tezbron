package com.example.bron.auth;

import com.example.bron.auth.dto.LoginRequestDto;
import com.example.bron.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        if (loginRequest.getLogin().equals("admin") && loginRequest.getPassword().equals("admin")) {
            return new LoginResponseDto("admin","admin","admin");
        }
        return null;
    }
}

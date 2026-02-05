package com.example.bron.auth.security;

import com.example.bron.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetails loadUserByUsername(String usernameOrPhone) {
    var user = userRepository.findByUsernameWithRolesAndPermissions(usernameOrPhone)
        .or(() -> userRepository.findByPhone(usernameOrPhone))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new CustomUserDetails(user);
  }
}

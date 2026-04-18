package com.example.bron.config;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.auth.user.role.RoleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private static final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

  private static final List<String> DEFAULT_ROLES = List.of(
      SUPER_ADMIN_ROLE,
      "ROLE_DISTRICT_ADMIN",
      "ROLE_OWNER",
      "ROLE_COACH",
      "ROLE_PLAYER"
  );

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${bootstrap.super-admin.username}")
  private String bootstrapUsername;

  @Value("${bootstrap.super-admin.password}")
  private String bootstrapPassword;

  @Value("${bootstrap.super-admin.full-name}")
  private String bootstrapFullName;

  @Value("${bootstrap.super-admin.phone}")
  private String bootstrapPhone;

  @Override
  public void run(String... args) {
    seedRoles();
    seedSuperAdmin();
  }

  private void seedRoles() {
    for (String name : DEFAULT_ROLES) {
      if (!roleRepository.existsByName(name)) {
        RoleEntity role = new RoleEntity();
        role.setName(name);
        roleRepository.save(role);
      }
    }
  }

  private void seedSuperAdmin() {
    if (userRepository.findByUsername(bootstrapUsername).isPresent()) {
      return;
    }

    RoleEntity superAdminRole = roleRepository.findByName(SUPER_ADMIN_ROLE)
        .orElseThrow(() -> new IllegalStateException(
            SUPER_ADMIN_ROLE + " role not found after seeding"));

    UserEntity admin = new UserEntity();
    admin.setUsername(bootstrapUsername);
    admin.setPasswordHash(passwordEncoder.encode(bootstrapPassword));
    admin.setFullName(bootstrapFullName);
    admin.setPhone(bootstrapPhone);
    admin.setCreatedAt(LocalDateTime.now());
    admin.getRoles().add(superAdminRole);

    userRepository.save(admin);
    log.info("Bootstrap super admin created: username={}", bootstrapUsername);
  }
}

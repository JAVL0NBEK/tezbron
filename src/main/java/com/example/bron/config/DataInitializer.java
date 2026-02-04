package com.example.bron.config;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.auth.user.permission.PermissionEntity;
import com.example.bron.auth.user.permission.PermissionRepository;
import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.auth.user.role.RoleRepository;
import com.example.bron.enums.Permission;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

//  @Override
//  public void run(String... args) throws Exception {
//    // 1. Permissionlar yaratish
//    for (Permission p : Permission.values()) {
//      permissionRepository.findByName(p.name())
//          .orElseGet(() -> permissionRepository.save(
//              new PermissionEntity(p.name())
//          ));
//    }
//    // 2. SUPER ADMIN role
//    RoleEntity superAdminRole = roleRepository.findByName("ROLE_SUPER_ADMIN")
//        .orElseGet(() -> {
//          RoleEntity role = new RoleEntity();
//          role.setName("ROLE_SUPER_ADMIN");
//          role.setPermissions(new HashSet<>(permissionRepository.findAll()));
//          return roleRepository.save(role);
//        });
//
//    // 3. Super Admin user
//    userRepository.findByUsername("superadmin")
//        .orElseGet(() -> {
//          UserEntity user = new UserEntity();
//          user.setUsername("superadmin");
//          user.setPasswordHash(passwordEncoder.encode("superadmin"));
//          user.setCreatedAt(LocalDateTime.now());
//          user.setRoles(Set.of(superAdminRole));
//          return userRepository.save(user);
//        });
//
//  }
}

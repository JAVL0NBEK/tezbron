package com.example.bron.auth.security;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.permission.PermissionEntity;
import com.example.bron.auth.user.role.RoleEntity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

  private final Long id;
  private final String username;
  private final String password;
  private final Set<GrantedAuthority> authorities;

  public CustomUserDetails(UserEntity user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPasswordHash();

    Set<GrantedAuthority> auths = new HashSet<>();

    // ROLE lar (ROLE_ prefix bilan)
    for (RoleEntity role : user.getRoles()) {
      auths.add(new SimpleGrantedAuthority(role.getName()));

      // PERMISSION lar
      for (PermissionEntity perm : role.getPermissions()) {
        auths.add(new SimpleGrantedAuthority(perm.getName()));
      }
    }

    this.authorities = auths;
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
  @Override public String getPassword() { return password; }
  @Override public String getUsername() { return username; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }
}

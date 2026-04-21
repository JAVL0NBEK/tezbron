package com.example.bron.auth.security;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.ForbiddenException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.exception.UnauthorizedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

  public static final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";
  public static final String DISTRICT_ADMIN_ROLE = "ROLE_DISTRICT_ADMIN";
  public static final String OWNER_ROLE = "ROLE_OWNER";

  private final UserRepository userRepository;

  public UserEntity getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
      throw new UnauthorizedException("UNAUTHENTICATED");
    }
    String username = auth.getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("current_user_not_found", List.of(username)));
  }

  public boolean isSuperAdmin() {
    return hasAuthority(SUPER_ADMIN_ROLE);
  }

  public boolean isDistrictAdmin() {
    return hasAuthority(DISTRICT_ADMIN_ROLE);
  }

  public boolean isOwner() {
    return hasAuthority(OWNER_ROLE);
  }

  public boolean hasAuthority(String authority) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return false;
    }
    return auth.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals(authority));
  }

  public Long getCurrentDistrictId() {
    var district = getCurrentUser().getDistrict();
    return district == null ? null : district.getId();
  }

  public void requireSameDistrict(Long targetDistrictId) {
    if (isSuperAdmin()) {
      return;
    }
    Long mine = getCurrentDistrictId();
    if (mine == null || targetDistrictId == null || !mine.equals(targetDistrictId)) {
      throw new ForbiddenException("DISTRICT_SCOPE_VIOLATION");
    }
  }

  public void requireOwnerOrDistrictScope(Long stadiumOwnerId, Long stadiumDistrictId) {
    if (isSuperAdmin()) {
      return;
    }
    if (isOwner()) {
      Long myId = getCurrentUser().getId();
      if (myId.equals(stadiumOwnerId)) {
        return;
      }
    }
    if (isDistrictAdmin()) {
      requireSameDistrict(stadiumDistrictId);
      return;
    }
    throw new ForbiddenException("STADIUM_SCOPE_VIOLATION");
  }
}
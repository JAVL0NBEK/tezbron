package com.example.bron.enums;

public enum StaffRole {
  SUPER_ADMIN("ROLE_SUPER_ADMIN"),
  DISTRICT_ADMIN("ROLE_DISTRICT_ADMIN"),
  OWNER("ROLE_OWNER"),
  COACH("ROLE_COACH");

  private final String roleName;

  StaffRole(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName;
  }
}

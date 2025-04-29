package com.logonedigital.pilot.user.domain.enums;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum Role {
  TECHDREAMER("ROLE_TECHDREAMER"),
  TECHMENTOR("ROLE_TECHMENTOR"),
  ADMIN("ROLE_ADMIN"),
  UNKNOWN("ROLE_UNKNOWN");

  Role(String role) {
    assert role != null && !role.trim().isEmpty() : "role cannot be null or empty";
  }

  private static final Map<String, Role> ROLES = buildRoles();

  private static Map<String, Role> buildRoles() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(Role::key, Function.identity()));
  }

  public String key() {
    return name();
  }

  public static Role from(String role) {
    assert role != null && !role.trim().isEmpty() : "role cannot be null or empty";
    return ROLES.getOrDefault(role, UNKNOWN);
  }
}

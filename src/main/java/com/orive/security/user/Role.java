package com.orive.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.orive.security.user.Permission.MP_CREATE;
import static com.orive.security.user.Permission.MP_DELETE;
import static com.orive.security.user.Permission.MP_READ;
import static com.orive.security.user.Permission.MP_UPDATE;
import static com.orive.security.user.Permission.MLA_CREATE;
import static com.orive.security.user.Permission.MLA_DELETE;
import static com.orive.security.user.Permission.MLA_READ;
import static com.orive.security.user.Permission.MLA_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  MP(
          Set.of(
                  MP_READ,
                  MP_UPDATE,
                  MP_DELETE,
                  MP_CREATE,
                  MLA_READ,
                  MLA_UPDATE,
                  MLA_DELETE,
                  MLA_CREATE
          )
  ),
  MLA(
          Set.of(
                  MLA_READ,
                  MLA_UPDATE,
                  MLA_DELETE,
                  MLA_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}

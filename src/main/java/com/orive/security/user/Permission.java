package com.orive.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    MP_READ("mp:read"),
    MP_UPDATE("mp:update"),
    MP_CREATE("mp:create"),
    MP_DELETE("mp:delete"),
    MLA_READ("mla:read"),
    MLA_UPDATE("mla:update"),
    MLA_CREATE("mla:create"),
    MLA_DELETE("mla:delete")

    ;

    @Getter
    private final String permission;
}

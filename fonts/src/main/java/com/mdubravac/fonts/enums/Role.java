package com.mdubravac.fonts.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    SUPER_USER,
    ;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

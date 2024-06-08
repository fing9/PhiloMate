package com.philomate.feed.user.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String name;

    public static Role from(String s) {
        return Role.valueOf(s.toUpperCase());
    }

    public static Role fromName(String s) {
        return Role.valueOf(s.substring(5).toUpperCase());
    }

}

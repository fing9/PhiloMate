package com.philomate.feed.global.security;

import com.philomate.feed.user.domain.enums.Role;
import org.springframework.security.core.Authentication;

public class SecurityUtils {

    public static Role extractRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .filter(authority -> authority.getAuthority().startsWith("ROLE_")).findFirst()
            .map(authority -> Role.fromName(authority.getAuthority()))
            .orElseThrow(() -> new IllegalArgumentException("권한을 찾을 수 없습니다."));
    }

}

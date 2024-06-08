package com.philomate.feed.global.security;

import com.philomate.feed.user.domain.enums.Role;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private static final Set<String> ROLE_NAMES = Stream.of(Role.values())
        .map(Objects::toString)
        .collect(Collectors.toSet());

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities =
            Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractRealmRoles(jwt).stream()).collect(
                Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username"));
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return Set.of();
        }

        Object rolesObject = realmAccess.get("roles");
        if (rolesObject instanceof Collection<?> rolesCollection) {
            Collection<String> roles = rolesCollection.stream()
                .filter(obj -> obj instanceof String)
                .map(String.class::cast)
                .collect(Collectors.toList());
            return createAuthorities(roles);
        } else {
            return Set.of();
        }
    }

    private Collection<GrantedAuthority> createAuthorities(Collection<String> roles) {
        return roles.stream()
            .filter(ROLE_NAMES::contains)
            .map(role ->  new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
    }

}
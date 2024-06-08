package com.philomate.feed.global.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authorizeHttpRequests(req ->
                req
                    // 모두 접근 가능한 API
//                    .requestMatchers(HttpMethod.POST, url)
//                    .permitAll()
                    // 권한에 따라 접근 가능한 API
//                    .requestMatchers(HttpMethod.GET, url)
//                    .hasAnyRole(role, role)
                    .anyRequest()
                    .authenticated()
            );

        return http.build();
    }

}

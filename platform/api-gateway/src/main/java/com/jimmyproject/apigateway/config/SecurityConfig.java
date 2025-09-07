package com.jimmyproject.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(e->e.pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/api/v1/accounts/**").authenticated()
                                .pathMatchers("/api/customers/**").authenticated()
                        .pathMatchers("/api/transactions/**").authenticated())
                .oauth2ResourceServer(o->o.jwt(Customizer.withDefaults()));

        http.csrf(c->c.disable());
        return http.build();
    }

}

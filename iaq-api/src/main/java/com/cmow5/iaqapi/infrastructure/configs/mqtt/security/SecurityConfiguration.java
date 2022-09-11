package com.cmow5.iaqapi.infrastructure.configs.mqtt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // To configure method-level security
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests(
                    expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                            .antMatchers("/health/**").permitAll()
                            .anyRequest()
                            .authenticated())
            .oauth2ResourceServer().jwt();
        return http.build();
    }
}

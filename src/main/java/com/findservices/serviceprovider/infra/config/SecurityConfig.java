package com.findservices.serviceprovider.infra.config;

import com.findservices.serviceprovider.login.service.FilterToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true, prePostEnabled = false)
public class SecurityConfig {

    @Autowired
    private FilterToken filter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/signin",
                        "/auth/register",
                        "/api/user/uploadPhoto"
                ).anonymous()
                .requestMatchers(
                        "/api/message/**",
                        "/api/serviceRequest/**",
                        "/api/serviceProvider/**",
                        "/api/country/**",
                        "/api/state/**",
                        "/api/city/**",
                        "/api/city/**",
                        "/api/user/**",
                        "/api/user",
                        "/api/profileEvaluation/**",
                        "/api/address",
                        "/api/address/**"
                ).authenticated()
                .and() //
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.whisper.config.security;

import com.whisper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurity {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurity(JwtAuthFilter jwtAuthFilter,UserService userService,PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                        x.requestMatchers(
                                "/auth/generateToken/**",
                                "/auth/isExpiredToken",
                                "/whisper/getUrlName/**",
                                "/whisper/category/**",
                                "/whisper/getWhispers/**",
                                "/whisper/getPendingWhispers/**",
                                "/user/createUser/**",
                                "/user/id/**",
                                "/user/username/**",
                                "/whisper/getBestUserPoint/**",
                                "/whisper/getUserWhispers/**",
                                "/whisper/carousel/big/**",
                                "/whisper/carousel/small/**",
                                "/whisper/getId/**",
                                "/whisper/getWhispersFilter/**",
                                "/dispute/getDispute/**",
                                "/dispute/getAll/**",
                                "/dispute/getMostUsedTags/**",
                                "/dispute/getDisputeTag/**",
                                "/user/getUserBadges/**"
                        ).permitAll()
                ).authorizeHttpRequests(x ->
                        x.requestMatchers(
                                "/user/**",
                                "/auth/logout/**",
                                "/whisper/locked/**",
                                "/whisper/control/like/**",
                                "/whisper/control/dislike/**",
                                "/dispute/control/like/**",
                                "/dispute/control/dislike/**",
                                "/user/getUsers/**",
                                "/user/getMods/**",
                                "/user/updateRole/**",
                                "/whisper/updateActive/**",
                                "/dispute/create/**",
                                "/dispute/updateDispute/**",
                                "/dispute/deleteDispute/**",
                                "/whisper/like/**",
                                "/whisper/dislike/**",
                                "/whisper/unlike/**",
                                "/whisper/undislike/**",
                                "/dispute/like/**",
                                "/dispute/dislike/**",
                                "/dispute/unlike/**",
                                "/dispute/undislike/**",
                                "/dispute/createComment/**",
                                "/dispute/deleteComment/**",
                                "/user/updatePlan/**",
                                "/user/updateUser/**",
                                "/user/getSubscribe/**",
                                "/user/writeLimitDrop/**",
                                "/user/allBadges",
                                "/notify/**"
                        ).authenticated()
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
}
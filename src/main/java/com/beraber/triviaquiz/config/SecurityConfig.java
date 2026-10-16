package com.beraber.triviaquiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Oturum çerezi SameSite=Lax ile gelir; API yalnızca aynı site (static sayfalar) üzerinden kullanılır.
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .headers(h -> h.frameOptions(Customizer.withDefaults()))
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .authorizeHttpRequests(auth -> auth
                // Sayfalar ve herkese açık uçlar
                .requestMatchers("/", "/index.html", "/admin.html", "/favicon.ico", "/error").permitAll()
                .requestMatchers("/api/users/login", "/api/users/register", "/api/users/logout").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/leaderboard", "/api/categories").permitAll()
                // Yönetici işlemleri
                .requestMatchers(HttpMethod.POST, "/api/categories", "/api/questions").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/questions/**", "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users", "/api/questions", "/api/questions/category/**").hasRole("ADMIN")
                // Oyun uçları (giriş yapmış kullanıcı)
                .anyRequest().authenticated()
            );
        return http.build();
    }
}

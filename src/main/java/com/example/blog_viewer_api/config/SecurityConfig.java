package com.example.blog_viewer_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {

        // Create in-memory admin user
        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder.encode("admin123")) // your password
                    .roles("ADMIN")
                    .build();
            return new InMemoryUserDetailsManager(admin);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        // Security rules
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable) // disable for simplicity (enable later with tokens)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/blogs","/api/blogs/*").permitAll() // default allow
                            .requestMatchers("/api/blogs/**").hasRole("ADMIN") // override for write
                            .anyRequest().authenticated()
                    )
                .httpBasic(Customizer.withDefaults());
            // use basic auth (username+password in Postman)

            return http.build();
        }
    }

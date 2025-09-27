package com.example.blog_viewer_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration
    public class SecurityConfig {

        // Create in-memory admin user
        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
            UserDetails admin1 = User.withUsername("admin1")
                    .password(passwordEncoder.encode("admin_1_123")) // your password
                    .roles("ADMIN")
                    .build();

            UserDetails admin2 = User.withUsername("admin2")
                    .password(passwordEncoder.encode("admin_2_123")) // your password
                    .roles("ADMIN")
                    .build();

            UserDetails admin3 = User.withUsername("admin3")
                    .password(passwordEncoder.encode("admin_3_123")) // your password
                    .roles("ADMIN")
                    .build();

            return new InMemoryUserDetailsManager(admin1, admin2, admin3);
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
                            .requestMatchers(HttpMethod.GET, "/api/blogs/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/blogs/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/blogs/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/blogs/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/blogs/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )

                    .httpBasic(Customizer.withDefaults());
            // use basic auth (username+password in Postman)

            return http.build();
        }
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration=new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setAllowedMethods(List.of("GET","POST","PUT","OPTIONS","DELETE"));
            configuration.setAllowCredentials(true);


            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            return source;
        }
    }

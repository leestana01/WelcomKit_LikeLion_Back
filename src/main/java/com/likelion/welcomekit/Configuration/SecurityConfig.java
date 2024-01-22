package com.likelion.welcomekit.Configuration;

import com.likelion.welcomekit.Utils.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests( request -> request
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/managers/**").hasAnyRole("MANAGER","BOSS","ADMIN")
                        .requestMatchers("/api/v1/users/**", "api/v1/letters/**").hasAnyRole("USER","MANAGER","BOSS","ADMIN")

                        .requestMatchers("/api/v1/settings/active").permitAll()
                        .requestMatchers("/api/v1/settings/**").hasAnyRole("BOSS","ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(withDefaults());

        return http.build();
    }
}

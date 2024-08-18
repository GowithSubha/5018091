package com.example.Bookstore.config;

import com.example.Bookstore.security.JwtAuthenticationEntryPoint;
import com.example.Bookstore.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;



    private final String[] PUBLIC_URLS = {
            "/auth/login"
            ,"/swagger-ui/**", "/v3/api-docs/**"
            ,"/v2/api-docs/**", "/swagger-resources/**"
            ,"/swagger-ui.html", "/webjars/**"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/books/**", "/customers/**")
                .authenticated()
                .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

 
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

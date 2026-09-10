package com.example.foodservice.common.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final SecurityEntryPoint securityEntryPoint;
    private final SecurityDeniedHandler securityDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(auth ->

                auth.requestMatchers("/static/**").permitAll()


                        .requestMatchers("/", "/index.html", "/favicon.ico").permitAll()
                        .requestMatchers("/brand/{brandId}/products").permitAll()

                        .requestMatchers("/user", "/user/login").permitAll()
                        .requestMatchers("/brand", "/brand/login").permitAll()
                        .requestMatchers("/store", "/store/login").permitAll()

                        .requestMatchers("/user/**").hasRole(SubjectType.USER.toString())
                        .requestMatchers("/store/**").hasRole(SubjectType.STORE.toString())
                        .requestMatchers("/brand/**").hasRole(SubjectType.BRAND.toString())
                        .requestMatchers("/order/**").hasRole(SubjectType.USER.toString())


                        .requestMatchers("/**").authenticated()
        );

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(securityEntryPoint));
        http.exceptionHandling(ex -> ex.accessDeniedHandler(securityDeniedHandler));
        return http.build();
    }
}

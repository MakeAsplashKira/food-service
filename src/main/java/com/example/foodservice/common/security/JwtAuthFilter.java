package com.example.foodservice.common.security;

import com.example.foodservice.common.security.principal.BrandPrincipal;
import com.example.foodservice.common.security.principal.StorePrincipal;
import com.example.foodservice.common.security.principal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;

import static com.example.foodservice.common.security.JwtService.BRAND_ID_KEY;
import static com.example.foodservice.common.security.JwtService.SUBJECT_TYPE_KEY;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        String rawToken = request.getHeader("Authorization");
        if(isTokenNotExist(rawToken)) {
            request.setAttribute("error", "Token required");
            filterChain.doFilter(request, response);
            return;
        }

        String token = rawToken.substring(7);

        try {
            Claims claims = jwtService.verifyAccessToken(token);

            SubjectType subjectType = SubjectType.valueOf(claims.get(SUBJECT_TYPE_KEY, String.class));

            Long id = Long.valueOf(claims.getSubject());


            Object principal = switch (subjectType) {
                case USER -> UserPrincipal.from(id);
                case STORE -> StorePrincipal.from(id, claims.get(BRAND_ID_KEY, Long.class));
                case BRAND -> BrandPrincipal.from(id);
            };

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + subjectType))
                    );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException e) {
            request.setAttribute("error", "Token is expired");
        } catch (JwtException | IllegalArgumentException e) {
            request.setAttribute("error", "Token is not valid");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenNotExist(String rawToken) {
        return rawToken == null || rawToken.isBlank() || !rawToken.startsWith("Bearer ");
    }
}

package org.mithwick93.accountable.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor()
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        if (shouldExtractAuthHeader(request)) {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring(7);
            try {
                Jwt jwt = jwtDecoder.decode(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.error("Error while extracting Auth claims: {}", e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }

    private boolean shouldExtractAuthHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String path = request.getServletPath();

        return authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ") &&
                !path.contains("/register") &&
                !path.contains("/login");
    }

}

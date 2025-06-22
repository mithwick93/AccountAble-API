package org.mithwick93.accountable.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_CAPACITY_PER_CLIENT = 200;

    private static final int TOKENS_TO_REFILL = 50;

    private static final int MAX_CAPACITY_UNAUTHENTICATED = 20;

    private static final int TOKENS_TO_REFILL_UNAUTHENTICATED = 5;

    private static final String[] UNAUTHENTICATED_ENDPOINTS = {
            "/api/auth/register",
            "/api/auth/verify-email",
            "/api/auth/login",
            "/api/auth/refresh-token",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String clientIp = getClientIp(request);
        boolean isUnauthenticatedEndpoint = getIsUnauthenticatedEndpoint(request.getRequestURI());
        Bucket bucket = resolveBucket(clientIp, isUnauthenticatedEndpoint);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            log.warn("Rate limit exceeded for IP: {}, method: {}, uri: {}", clientIp, request.getMethod(), request.getRequestURI());
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");
        }
    }

    private Bucket resolveBucket(String clientIp, boolean isUnauthenticatedEndpoint) {
        String key = clientIp + (isUnauthenticatedEndpoint ? ":unauthenticated" : ":authenticated");
        Bandwidth limit = isUnauthenticatedEndpoint
                ? Bandwidth.builder().capacity(MAX_CAPACITY_UNAUTHENTICATED).refillIntervally(TOKENS_TO_REFILL_UNAUTHENTICATED, Duration.ofSeconds(10)).build()
                : Bandwidth.builder().capacity(MAX_CAPACITY_PER_CLIENT).refillIntervally(TOKENS_TO_REFILL, Duration.ofSeconds(10)).build();
        return buckets.computeIfAbsent(key, k -> Bucket.builder().addLimit(limit).build());
    }

    private boolean getIsUnauthenticatedEndpoint(String uri) {
        for (String pattern : UNAUTHENTICATED_ENDPOINTS) {
            if (pathMatcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String cfConnectingIp = request.getHeader("CF-Connecting-IP");
        if (cfConnectingIp != null && !cfConnectingIp.isEmpty()) {
            return cfConnectingIp;
        }
        return request.getRemoteAddr();
    }

}

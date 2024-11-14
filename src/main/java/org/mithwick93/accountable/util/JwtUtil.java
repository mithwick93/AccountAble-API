package org.mithwick93.accountable.util;

import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {
    private final JwtEncoder jwtEncoder;

    private long expireInSeconds;

    @Autowired
    public JwtUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${jwt.expire-in-seconds:3600}")
    public void setExpireInSeconds(long expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public String generateToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expireInSeconds))
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Integer.parseInt(jwt.getClaims().get("sub").toString());
    }
}

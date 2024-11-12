package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

@Service
public class UserServiceImpl extends BaseService implements UserService {
    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            JwtEncoder jwtEncoder,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public String createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("User name: %s already exists".formatted(user.getUsername()));
        }

        User newUser = userRepository.save(user);

        return generateToken(newUser);
    }

    @Override
    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(INVALID_USER_NAME_PASSWORD);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw INVALID_USER_NAME_PASSWORD.get();
        }

        return generateToken(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAllById(List.of((long) getAuthenticatedUserId()));
    }

    private String generateToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

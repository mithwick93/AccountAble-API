package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService extends BaseService {
    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public String createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("User name: %s already exists".formatted(user.getUsername()));
        }

        User newUser = userRepository.save(user);

        return jwtUtil.generateToken(newUser);
    }

    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(INVALID_USER_NAME_PASSWORD);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw INVALID_USER_NAME_PASSWORD.get();
        }

        return jwtUtil.generateToken(user);
    }

    public List<User> listUsers() {
        return userRepository.findAllById(List.of((long) getAuthenticatedUserId()));
    }
}

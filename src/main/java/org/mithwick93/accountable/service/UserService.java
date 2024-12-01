package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @CachePut(value = "user_cache", key = "#result.id")
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("User name: %s already exists".formatted(user.getUsername()));
        }

        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> validate(user, password))
                .orElseThrow(INVALID_USER_NAME_PASSWORD);
    }

    private User validate(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw INVALID_USER_NAME_PASSWORD.get();
        }
        return user;
    }

}

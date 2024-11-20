package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("User name: %s already exists".formatted(user.getUsername()));
        }

        userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> validateUser(user, password))
                .orElseThrow(INVALID_USER_NAME_PASSWORD);
    }

    public List<User> listUsers(int userId) {
        return userRepository.findAllById(List.of(userId));
    }

    private User validateUser(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw INVALID_USER_NAME_PASSWORD.get();
        }
        return user;
    }

}

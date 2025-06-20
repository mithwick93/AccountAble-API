package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.dal.repository.specification.UserRegistrationRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.gateway.EmailGateway;
import org.mithwick93.accountable.gateway.dto.request.EmailRequest;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.model.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");

    private final UserRegistrationRepository registrationRepository;

    private final UserRepository userRepository;

    private final EmailGateway emailGateway;

    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.url}")
    private String frontendDomain;

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @CachePut(value = "user_cache", key = "#result.id", unless = "#result == null")
    public UserRegistration create(User user) {
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new BadRequestException(
                    "User name: %s or email: %s already exists".formatted(user.getUsername(), user.getEmail())
            );
        }

        if (registrationRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new BadRequestException("Registration request already exists");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        UserRegistration pending = new UserRegistration();
        pending.setFirstName(user.getFirstName());
        pending.setLastName(user.getLastName());
        pending.setUsername(user.getUsername());
        pending.setEmail(user.getEmail());
        pending.setPasswordHash(user.getPasswordHash());
        pending.setToken(token);
        pending.setExpiresAt(expiresAt);

        UserRegistration saved = registrationRepository.save(pending);
        sendVerificationEmail(saved);

        return saved;
    }

    public User validateEmail(String token) {
        UserRegistration registration = registrationRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Invalid Request. Please register again"));

        if (registration.getExpiresAt().isBefore(LocalDateTime.now())) {
            registrationRepository.delete(registration);
            throw new BadRequestException("Request has expired. Please register again");
        }

        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setUsername(registration.getUsername());
        user.setEmail(registration.getEmail());
        user.setPasswordHash(registration.getPasswordHash());

        User savedUser = userRepository.save(user);
        registrationRepository.delete(registration);

        return savedUser;
    }

    public User authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> validate(user, password))
                .orElseThrow(INVALID_USER_NAME_PASSWORD);
    }

    public void changePassword(int userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BadRequestException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, user.getPasswordHash())) {
            throw new BadRequestException("New password must be different from the old password");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private User validate(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw INVALID_USER_NAME_PASSWORD.get();
        }
        return user;
    }

    private void sendVerificationEmail(UserRegistration registration) {
        String fullName = registration.getFirstName() + " " + registration.getLastName();
        String verificationLink = frontendDomain + "/verify-email?token=" + registration.getToken();

        String htmlContent = """
                <html>
                    <body>
                        <h2>Account Verification</h2>
                        <p>Dear %s,</p>
                        <p>Thank you for registering. Please verify your account by clicking the link below:</p>
                        <p>
                            <a href="%s">Verify Account</a>
                        </p>
                        <p>This link will expire in 24 hours from the time you received this email.</p>
                        <p>If you did not request this, please ignore this email.</p>
                        <br>
                        <p>Best regards,<br>AccountAble Team</p>
                    </body>
                </html>
                """.formatted(fullName, verificationLink);

        emailGateway.sendEmail(
                new EmailRequest(
                        fullName,
                        registration.getEmail(),
                        "AccountAble Email Verification",
                        null,
                        htmlContent
                )
        );
    }

}

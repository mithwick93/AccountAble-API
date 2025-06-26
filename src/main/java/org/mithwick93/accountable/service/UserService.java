package org.mithwick93.accountable.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mithwick93.accountable.dal.repository.PasswordResetTokenRepository;
import org.mithwick93.accountable.dal.repository.UserRegistrationRepository;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.gateway.EmailGateway;
import org.mithwick93.accountable.gateway.GoogleTokenVerifier;
import org.mithwick93.accountable.gateway.dto.request.EmailRequest;
import org.mithwick93.accountable.model.PasswordResetToken;
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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService {

    public static final Supplier<AuthException> INVALID_USER_NAME_PASSWORD
            = AuthException.supplier("Invalid user name / password");

    private final UserRegistrationRepository registrationRepository;

    private final UserRepository userRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final EmailGateway emailGateway;

    private final PasswordEncoder passwordEncoder;

    private final GoogleTokenVerifier googleTokenVerifier;

    @Value("${frontend.url}")
    private String frontendDomain;

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @CachePut(value = "user_cache", key = "#result.id", unless = "#result == null")
    public void create(User user) {
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
    }

    public void validateEmail(String token) {
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

        userRepository.save(user);
        registrationRepository.delete(registration);
    }

    public User authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> validate(user, password))
                .orElseThrow(INVALID_USER_NAME_PASSWORD);
    }

    public User authenticateWithGoogle(String idToken) {
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idToken);
        if (payload.isEmpty()) {
            throw new BadRequestException("Invalid Token");
        }

        String email = payload.getEmail();

        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("User with email {} not found, creating user", email);

                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName((String) payload.get("given_name"));
                    newUser.setLastName((String) payload.get("family_name"));
                    newUser.setUsername(UUID.randomUUID().toString());
                    newUser.setPasswordHash(passwordEncoder.encode(UUID.randomUUID().toString()));
                    return userRepository.save(newUser);
                });
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

    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            log.warn("No user found with email {} for password reset", email);
            return;
        }
        User user = userOptional.get();

        passwordResetTokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(resetToken);
        sendPasswordResetEmail(resetToken);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken reset = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Invalid Request. Please request a new password reset"));

        if (reset.getExpiresAt().isBefore(LocalDateTime.now())) {
            passwordResetTokenRepository.delete(reset);
            throw new BadRequestException("Token expired");
        }

        User user = reset.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(reset);
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

    private void sendPasswordResetEmail(PasswordResetToken resetToken) {
        String fullName = resetToken.getUser().getFirstName() + " " + resetToken.getUser().getLastName();
        String resetLink = frontendDomain + "/reset-password?token=" + resetToken.getToken();
        String htmlContent = """
                <html>
                    <body>
                        <h2>Password Reset Request</h2>
                        <p>Dear %s,</p>
                        <p>We received a request to reset your password. Please click the link below to reset your password:</p>
                        <p>
                            <a href="%s">Reset Password</a>
                        </p>
                        <p>This link will expire in 30 minutes from the time you received this email.</p>
                        <p>If you did not request this, please ignore this email.</p>
                        <br>
                        <p>Best regards,<br>AccountAble Team</p>
                    </body>
                </html>
                """.formatted(fullName, resetLink);

        emailGateway.sendEmail(
                new EmailRequest(
                        fullName,
                        resetToken.getUser().getEmail(),
                        "AccountAble Password Reset",
                        null,
                        htmlContent
                )
        );
    }

}

package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.RefreshTokenRepository;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.exception.AuthException;
import org.mithwick93.accountable.model.RefreshToken;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private long refreshTokenDurationSeconds;

    @Autowired
    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Value("${refresh-token.expire-in-seconds:864000}")
    public void setRefreshTokenDurationSeconds(long refreshTokenDurationSeconds) {
        this.refreshTokenDurationSeconds = refreshTokenDurationSeconds;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationSeconds));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public User refreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new AuthException("Invalid refresh token"));
    }

    @Transactional
    public void deleteByUserId(int userId) {
        userRepository
                .findById((long) userId)
                .map(refreshTokenRepository::deleteByUser)
                .orElseThrow(() -> new AuthException("User with id " + userId + " not found"));
    }

    private RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException("Refresh token was expired. Please make a new login request");
        }

        return refreshToken;
    }
}

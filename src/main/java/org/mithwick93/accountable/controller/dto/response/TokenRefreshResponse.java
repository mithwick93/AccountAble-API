package org.mithwick93.accountable.controller.dto.response;

public record TokenRefreshResponse(
        String tokenType,
        String accessToken,
        String refreshToken
) {
    public static TokenRefreshResponse of(String accessToken, String refreshToken) {
        return new TokenRefreshResponse("Bearer", accessToken, refreshToken);
    }
}

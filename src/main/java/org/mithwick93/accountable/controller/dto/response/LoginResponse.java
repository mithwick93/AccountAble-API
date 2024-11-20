package org.mithwick93.accountable.controller.dto.response;

public record LoginResponse(
        String tokenType,
        String accessToken,
        String refreshToken
) {

    public static LoginResponse of(String accessToken, String refreshToken) {
        return new LoginResponse("Bearer", accessToken, refreshToken);
    }

}

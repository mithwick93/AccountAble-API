package org.mithwick93.accountable.controller.dto.response;

public record RegistrationResponse(
        String accessToken
) {
    public static RegistrationResponse of(String accessToken) {
        return new RegistrationResponse(accessToken);
    }
}

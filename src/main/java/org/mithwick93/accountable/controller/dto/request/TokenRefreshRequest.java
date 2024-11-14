package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
        @NotBlank
        String refreshToken
) {
}

package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyEmailRequest(
        @NotBlank(message = "Token is required")
        String token
) {

}

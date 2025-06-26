package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequest(
        @NotBlank(message = "ID Token is required")
        String idToken
) {

}

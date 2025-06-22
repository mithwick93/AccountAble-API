package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.mithwick93.accountable.validation.ValidPassword;

public record ResetPasswordRequest(
        @NotBlank(message = "Token is required")
        String token,

        @ValidPassword
        String newPassword
) {

}
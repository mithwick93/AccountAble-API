package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.validation.ValidPassword;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
        String username,

        @ValidPassword
        String password
) {

}

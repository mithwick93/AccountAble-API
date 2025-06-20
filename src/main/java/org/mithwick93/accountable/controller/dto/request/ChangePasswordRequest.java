package org.mithwick93.accountable.controller.dto.request;

import org.mithwick93.accountable.validation.ValidPassword;

public record ChangePasswordRequest(
        @ValidPassword
        String oldPassword,

        @ValidPassword
        String newPassword
) {

}
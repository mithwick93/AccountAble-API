package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        int id,
        String firstName,
        String lastName,
        String username,
        LocalDateTime created,
        LocalDateTime modified
) {

}

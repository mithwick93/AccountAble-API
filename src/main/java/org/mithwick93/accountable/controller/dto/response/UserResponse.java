package org.mithwick93.accountable.controller.dto.response;

public record UserResponse(
        int id,
        String firstName,
        String lastName,
        String username
) {
}

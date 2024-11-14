package org.mithwick93.accountable.controller.dto.response;

public record MessageResponse(
        String message
) {
    public static MessageResponse of(String message) {
        return new MessageResponse(message);
    }
}

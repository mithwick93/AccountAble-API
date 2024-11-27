package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record TransactionCategoryResponse(
        int id,
        String name,
        String type,
        UserResponse user,
        LocalDateTime created,
        LocalDateTime modified
) {

}

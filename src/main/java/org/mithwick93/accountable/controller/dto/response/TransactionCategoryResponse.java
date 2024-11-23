package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record TransactionCategoryResponse(
        int id,
        String name,
        String type,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

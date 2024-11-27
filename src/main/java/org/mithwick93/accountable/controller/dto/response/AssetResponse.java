package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssetResponse(
        Long id,
        String type,
        String name,
        String description,
        BigDecimal balance,
        String currency,
        UserResponse user,
        LocalDateTime created,
        LocalDateTime modified
) {

}

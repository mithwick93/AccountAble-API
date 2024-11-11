package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;

public record AssetResponse(
        Long id,
        String type,
        String name,
        String description,
        BigDecimal balance,
        String currency,
        int userId
) {
}

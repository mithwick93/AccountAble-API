package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentSystemDebitResponse(
        int id,
        String name,
        String description,
        String currency,
        AssetResponse asset,
        BigDecimal dailyLimit,
        UserResponse user,
        LocalDateTime created,
        LocalDateTime modified
) {

}

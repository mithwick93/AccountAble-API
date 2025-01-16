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
        boolean active,
        String cardHolderName,
        String cardNumber,
        String securityCode,
        String expiryDate,
        String additionalNote,
        LocalDateTime created,
        LocalDateTime modified
) {

}

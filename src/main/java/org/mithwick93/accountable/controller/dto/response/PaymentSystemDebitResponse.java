package org.mithwick93.accountable.controller.dto.response;

import org.mithwick93.accountable.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentSystemDebitResponse(
        int id,
        String name,
        String description,
        Currency currency,
        int assetId,
        BigDecimal dailyLimit,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

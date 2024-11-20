package org.mithwick93.accountable.controller.dto.response;

import org.mithwick93.accountable.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentSystemCreditResponse(
        int id,
        String name,
        String description,
        Currency currency,
        BigDecimal creditLimit,
        BigDecimal utilizedAmount,
        byte statementDay,
        byte dueDay,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

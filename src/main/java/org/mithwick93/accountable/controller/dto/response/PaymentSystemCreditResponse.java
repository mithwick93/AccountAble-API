package org.mithwick93.accountable.controller.dto.response;

import org.mithwick93.accountable.model.Currency;

import java.math.BigDecimal;

public record PaymentSystemCreditResponse(
        int id,
        String name,
        String description,
        Currency currency,
        BigDecimal creditLimit,
        BigDecimal utilizedAmount,
        byte statementDate,
        byte dueDate,
        int userId
) {
}

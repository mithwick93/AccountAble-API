package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentSystemCreditResponse(
        int id,
        String name,
        String description,
        String currency,
        BigDecimal creditLimit,
        BigDecimal utilizedAmount,
        byte statementDay,
        byte dueDay,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

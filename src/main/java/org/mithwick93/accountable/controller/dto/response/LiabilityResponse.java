package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LiabilityResponse(
        int id,
        String name,
        String description,
        String type,
        String currency,
        UserResponse user,
        BigDecimal amount,
        BigDecimal balance,
        BigDecimal interestRate,
        Byte statementDay,
        byte dueDay,
        String status,
        LocalDateTime created,
        LocalDateTime modified
) {

}

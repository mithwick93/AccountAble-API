package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SharedTransactionResponse(
        long id,
        UserResponse user,
        BigDecimal share,
        BigDecimal paidAmount,
        boolean isSettled,
        LocalDateTime created,
        LocalDateTime modified
) {

}

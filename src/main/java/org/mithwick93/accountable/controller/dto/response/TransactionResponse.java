package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        long id,
        String name,
        String description,
        String type,
        Integer categoryId,
        BigDecimal amount,
        String currency,
        LocalDateTime date,
        Integer fromAssetId,
        Integer toAssetId,
        Integer fromPaymentSystemId,
        Integer toPaymentSystemId,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

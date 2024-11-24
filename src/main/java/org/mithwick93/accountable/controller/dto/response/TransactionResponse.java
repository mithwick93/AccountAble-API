package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
        long id,
        String name,
        String description,
        String type,
        Integer categoryId,
        BigDecimal amount,
        String currency,
        LocalDate date,
        Integer fromAssetId,
        Integer toAssetId,
        Integer fromPaymentSystemId,
        Integer toPaymentSystemId,
        int userId,
        List<SharedTransactionResponse> sharedTransactions,
        LocalDateTime created,
        LocalDateTime modified
) {

}

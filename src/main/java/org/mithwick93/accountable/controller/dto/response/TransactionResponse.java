package org.mithwick93.accountable.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
        long id,

        String name,

        String description,

        String type,

        TransactionCategoryResponse category,

        BigDecimal amount,

        String currency,

        LocalDate date,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer fromAssetId,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer toAssetId,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer fromPaymentSystemId,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer toPaymentSystemId,

        UserResponse user,

        List<SharedTransactionResponse> sharedTransactions,

        LocalDateTime created,

        LocalDateTime modified
) {

}

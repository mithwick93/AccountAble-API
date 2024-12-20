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
        AssetResponse fromAsset,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        AssetResponse toAsset,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        PaymentSystemCreditResponse fromPaymentSystemCredit,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        PaymentSystemCreditResponse toPaymentSystemCredit,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        PaymentSystemDebitResponse fromPaymentSystemDebit,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        PaymentSystemDebitResponse toPaymentSystemDebit,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        LiabilityResponse fromLiability,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        LiabilityResponse toLiability,

        UserResponse user,

        List<SharedTransactionResponse> sharedTransactions,

        LocalDateTime created,

        LocalDateTime modified
) {

}

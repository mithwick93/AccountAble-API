package org.mithwick93.accountable.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionTemplateResponse(
        int id,

        String name,

        String description,

        String type,

        TransactionCategoryResponse category,

        BigDecimal amount,

        String currency,

        UserResponse user,

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

        String frequency,

        String dayOfWeek,

        Byte dayOfMonth,

        Byte monthOfYear,

        LocalDate startDate,

        LocalDate endDate,

        LocalDateTime created,

        LocalDateTime modified

) {

}

package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.TransactionType;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TransactionRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank(message = "Transaction type is required")
        @ValidEnum(enumClass = TransactionType.class, message = "must be a valid transaction type")
        String type,

        Integer categoryId,

        @NotNull(message = "Transaction amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Transaction amount must be greater than zero")
        BigDecimal amount,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        LocalDate date,

        Integer fromAssetId,

        Integer toAssetId,

        Integer fromPaymentSystemId,

        Integer toPaymentSystemId,

        @NotNull
        int userId,

        @NotNull
        List<SharedTransactionRequest> sharedTransactions
) {

}

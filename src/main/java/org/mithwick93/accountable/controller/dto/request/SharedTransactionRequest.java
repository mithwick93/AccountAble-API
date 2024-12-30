package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SharedTransactionRequest(
        Long id,

        @NotNull
        int userId,

        @DecimalMin(value = "0.0", inclusive = false, message = "Share amount must be greater than zero")
        @NotNull
        BigDecimal share,

        @DecimalMin(value = "0.0", message = "Paid amount must be non negative")
        @NotNull
        BigDecimal paidAmount,

        Boolean isSettled
) {

}

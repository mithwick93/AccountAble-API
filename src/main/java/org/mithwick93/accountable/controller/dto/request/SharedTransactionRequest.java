package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record SharedTransactionRequest(
        Long id,

        @NotBlank
        int userId,

        @DecimalMin(value = "0.0", inclusive = false, message = "Share amount must be greater than zero")
        BigDecimal share,

        @DecimalMin(value = "0.0", message = "Paid amount must be non negative")
        BigDecimal paidAmount,

        Boolean isSettled
) {

}

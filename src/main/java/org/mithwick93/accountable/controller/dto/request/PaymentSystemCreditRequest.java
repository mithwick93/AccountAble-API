package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mithwick93.accountable.model.Currency;

import java.math.BigDecimal;

public record PaymentSystemCreditRequest(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Currency is required")
        Currency currency,

        @NotNull(message = "Credit limit is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Credit limit must be greater than zero")
        BigDecimal creditLimit,

        @DecimalMin(value = "0.0", message = "Utilized amount must be non-negative")
        BigDecimal utilizedAmount,

        @NotNull(message = "Statement date is required")
        @Min(value = 1, message = "Statement date should be greater than or equal to 1")
        @Max(value = 31, message = "Statement date should be less than or equal to 31")
        byte statementDate,

        @NotNull(message = "Due date is required")
        @Min(value = 1, message = "Due date should be greater than or equal to 1")
        @Max(value = 31, message = "Due date should be less than or equal to 31")
        byte dueDate
) {
}
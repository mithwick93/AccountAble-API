package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;

public record PaymentSystemCreditRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "Credit limit must be greater than zero")
        BigDecimal creditLimit,

        @DecimalMin(value = "0.0", message = "Utilized amount must be non-negative")
        BigDecimal utilizedAmount,

        @NotNull
        @Min(value = 1, message = "Statement day should be greater than or equal to 1")
        @Max(value = 31, message = "Statement day should be less than or equal to 31")
        byte statementDay,

        @NotNull
        @Min(value = 1, message = "Due day should be greater than or equal to 1")
        @Max(value = 31, message = "Due day should be less than or equal to 31")
        byte dueDay
) {

}
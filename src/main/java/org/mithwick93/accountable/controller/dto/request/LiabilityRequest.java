package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.LiabilityStatus;
import org.mithwick93.accountable.model.LiabilityType;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;

public record LiabilityRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank
        @ValidEnum(enumClass = LiabilityType.class, message = "must be a valid liability type")
        String type,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal amount,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal balance,

        @DecimalMin("0.0")
        BigDecimal interestRate,

        @Min(value = 1, message = "Statement day should be greater than or equal to 1")
        @Max(value = 31, message = "Statement day should be less than or equal to 31")
        Byte statementDay,

        @NotNull
        @Min(value = 1, message = "Due day should be greater than or equal to 1")
        @Max(value = 31, message = "Due day should be less than or equal to 31")
        byte dueDay,

        @NotBlank
        @ValidEnum(enumClass = LiabilityStatus.class, message = "must be a valid liability status")
        String status
) {

}

package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.InstallmentPlanStatus;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InstallmentPlanRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotNull
        int liabilityId,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal installmentAmount,

        @NotNull
        @Min(1)
        int totalInstallments,

        @NotNull
        @Min(0)
        int installmentsPaid,

        @DecimalMin("0.0")
        BigDecimal interestRate,

        @NotNull
        LocalDate startDate,

        LocalDate endDate,

        @NotBlank
        @ValidEnum(enumClass = InstallmentPlanStatus.class, message = "must be a valid installment plan status")
        String status
) {

}

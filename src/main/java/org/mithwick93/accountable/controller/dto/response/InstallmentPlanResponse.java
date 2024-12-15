package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record InstallmentPlanResponse(
        int id,
        String name,
        String description,
        LiabilityResponse liability,
        String currency,
        UserResponse user,
        BigDecimal installmentAmount,
        int totalInstallments,
        int installmentsPaid,
        BigDecimal interestRate,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        LocalDateTime created,
        LocalDateTime modified
) {

}

package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExtractedTransaction(
        String name,
        String description,
        String type,
        TransactionCategoryResponse category,
        BigDecimal amount,
        String currency,
        LocalDate date
) {

}

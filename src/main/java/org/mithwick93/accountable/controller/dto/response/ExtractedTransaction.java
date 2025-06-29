package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;

public record ExtractedTransaction(
        String name,
        BigDecimal amount
) {

}

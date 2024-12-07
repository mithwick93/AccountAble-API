package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MarkTransactionsPaidRequest(
        @NotNull
        int userId,

        @NotEmpty
        List<Long> transactionIds
) {

}

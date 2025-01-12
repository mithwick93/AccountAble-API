package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record MarkTransactionsPaidRequest(
        @NotEmpty
        List<Long> sharedTransactionIds
) {

}

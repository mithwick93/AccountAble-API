package org.mithwick93.accountable.controller.dto.request;

import java.time.LocalDate;
import java.util.List;

public record TransactionSearchRequest(
        List<Integer> userIds,
        LocalDate dateFrom,
        LocalDate dateTo,
        List<String> types,
        List<Integer> categoryIds,
        List<Integer> fromAssetIds,
        List<Integer> toAssetIds,
        List<Integer> fromPaymentSystemIds,
        List<Integer> toPaymentSystemIds,
        Boolean hasPendingSettlements,
        Boolean hasSharedTransactions
) {

}

package org.mithwick93.accountable.controller.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TransactionSearchRequest(
        List<Integer> userIds,
        String name,
        String description,
        LocalDate dateFrom,
        LocalDate dateTo,
        List<String> currencies,
        BigDecimal amountFrom,
        BigDecimal amountTo,
        List<String> types,
        List<Integer> categoryIds,
        List<Integer> fromAssetIds,
        List<Integer> toAssetIds,
        List<Integer> fromPaymentSystemIds,
        List<Integer> toPaymentSystemIds,
        List<Integer> fromLiabilityIds,
        List<Integer> toLiabilityIds,
        Boolean hasPendingSettlements,
        Boolean hasSharedTransactions
) {

}

package org.mithwick93.accountable.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record TransactionSearch(
        Optional<List<Integer>> userIds,

        Optional<LocalDate> dateFrom,

        Optional<LocalDate> dateTo,

        Optional<List<String>> types,

        Optional<List<Integer>> categoryIds,

        Optional<List<Integer>> fromAssetIds,

        Optional<List<Integer>> toAssetIds,

        Optional<List<Integer>> fromPaymentSystemIds,

        Optional<List<Integer>> toPaymentSystemIds,

        Optional<Boolean> hasPendingSettlements,

        Optional<Boolean> hasSharedTransactions
) {

}

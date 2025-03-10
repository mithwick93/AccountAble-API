package org.mithwick93.accountable.model;

import org.mithwick93.accountable.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record TransactionSearch(
        Optional<List<Integer>> userIds,

        Optional<String> name,

        Optional<String> description,

        Optional<LocalDate> dateFrom,

        Optional<LocalDate> dateTo,

        Optional<List<Currency>> currencies,

        Optional<BigDecimal> amountFrom,

        Optional<BigDecimal> amountTo,

        Optional<List<String>> types,

        Optional<List<Integer>> categoryIds,

        Optional<List<Integer>> fromAssetIds,

        Optional<List<Integer>> toAssetIds,

        Optional<List<Integer>> fromPaymentSystemIds,

        Optional<List<Integer>> toPaymentSystemIds,

        Optional<List<Integer>> fromLiabilityIds,

        Optional<List<Integer>> toLiabilityIds,

        Optional<Boolean> hasPendingSettlements,

        Optional<Boolean> hasSharedTransactions
) {

}

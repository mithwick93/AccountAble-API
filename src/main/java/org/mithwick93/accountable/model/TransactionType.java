package org.mithwick93.accountable.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER;

    public static final List<String> TRANSACTION_TYPES = Arrays.stream(TransactionType.values())
            .map(TransactionType::name)
            .collect(Collectors.toList());
}

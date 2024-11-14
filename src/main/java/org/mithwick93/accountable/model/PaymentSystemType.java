package org.mithwick93.accountable.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum PaymentSystemType {
    DEBIT(1, "DEBIT", "Debit payment system, linked to an asset with a daily spending limit."),
    CREDIT(2, "CREDIT", "Credit payment system with a credit limit, utilized amount, and due date.");

    private static final Map<Integer, PaymentSystemType> lookup = Arrays.stream(values())
            .collect(Collectors.toMap(PaymentSystemType::getId, Function.identity()));
    private final int id;
    private final String name;
    private final String description;

    PaymentSystemType(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static PaymentSystemType fromId(int id) {
        return lookup.get(id);
    }
}

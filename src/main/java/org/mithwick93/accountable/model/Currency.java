package org.mithwick93.accountable.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Currency {
    LKR(1, "Sri Lankan Rupee", "The official currency of Sri Lanka"),
    SEK(2, "Swedish Krona", "The official currency of Sweden");

    private static final Map<Integer, Currency> lookup = Arrays.stream(values())
            .collect(Collectors.toMap(Currency::getId, Function.identity()));
    private final int id;
    private final String name;
    private final String description;

    Currency(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Currency fromId(int id) {
        return lookup.get(id);
    }
}

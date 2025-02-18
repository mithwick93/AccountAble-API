package org.mithwick93.accountable.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Currency {
    LKR(1, "Sri Lankan Rupee", "The official currency of Sri Lanka"),
    SEK(2, "Swedish Krona", "The official currency of Sweden"),
    USD(3, "United States Dollar", "The official currency of USA"),
    SGD(4, "Singapore Dollar", "The official currency of Singapore"),
    G24(5, "Gold 24 carat", "Gold 24 carat");

    private static final Map<Integer, Currency> lookup = Arrays.stream(values())
            .collect(Collectors.toMap(Currency::getId, Function.identity()));

    private final int id;

    private final String name;

    private final String description;

    public static Currency fromId(int id) {
        return lookup.get(id);
    }
}

package org.mithwick93.accountable.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum AssetType {
    SAVINGS_ACCOUNT(1),
    INVESTMENT(2);

    private static final Map<Integer, AssetType> lookup = Arrays.stream(AssetType.values())
            .collect(Collectors.toMap(AssetType::getId, Function.identity()));

    private final int id;

    public static AssetType fromId(int id) {
        return lookup.get(id);
    }

}
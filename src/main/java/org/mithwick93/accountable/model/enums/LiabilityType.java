package org.mithwick93.accountable.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum LiabilityType {
    CREDIT_CARD(1),
    PERSONAL_LOAN(2),
    MORTGAGE(3),
    AUTO_LOAN(4),
    STUDENT_LOAN(5),
    LINE_OF_CREDIT(6),
    BUSINESS_LOAN(7);

    private static final Map<Integer, LiabilityType> lookup = Arrays.stream(LiabilityType.values())
            .collect(Collectors.toMap(LiabilityType::getId, Function.identity()));

    private final int id;

    public static LiabilityType fromId(int id) {
        return lookup.get(id);
    }

}
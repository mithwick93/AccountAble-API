package org.mithwick93.accountable.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Frequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    public static final List<String> FREQUENCY_TYPES = Arrays.stream(Frequency.values())
            .map(Frequency::name)
            .collect(Collectors.toList());
}
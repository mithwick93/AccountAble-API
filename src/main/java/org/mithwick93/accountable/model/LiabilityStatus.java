package org.mithwick93.accountable.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LiabilityStatus {
    ACTIVE,
    SETTLED,
    DEFAULTED,
    CLOSED,
    IN_DISPUTE,
    SUSPENDED,
    PENDING_ACTIVATION;

    public static final List<String> LIABILITY_STATUSES = Arrays.stream(LiabilityStatus.values())
            .map(LiabilityStatus::name)
            .collect(Collectors.toList());

}

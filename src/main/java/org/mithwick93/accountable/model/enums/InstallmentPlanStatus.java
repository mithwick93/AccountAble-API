package org.mithwick93.accountable.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InstallmentPlanStatus {
    ACTIVE,
    SETTLED,
    DEFAULTED,
    CANCELED,
    DEFERRED,
    OVERDUE,
    RESTRUCTURED;

    public static final List<String> INSTALLMENT_PLAN_STATUSES = Arrays.stream(InstallmentPlanStatus.values())
            .map(InstallmentPlanStatus::name)
            .collect(Collectors.toList());

}

package org.mithwick93.accountable.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@DiscriminatorValue("1")
@Table(name = "payment_system_debits")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemDebit extends PaymentSystem {

    @Column(name = "asset_id", nullable = false, updatable = false)
    private int assetId;

    @Column(name = "daily_limit")
    @Nullable
    private BigDecimal dailyLimit;
}

package org.mithwick93.accountable.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mithwick93.accountable.model.converter.CurrencyConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    @Nullable
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Nullable
    private TransactionCategory category;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_asset_id")
    @Nullable
    private Asset fromAsset; // For TRANSFER: asset being transferred from

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_asset_id")
    @Nullable
    private Asset toAsset; // For TRANSFER or INCOME: asset being transferred to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_payment_system_id")
    @Nullable
    private PaymentSystem fromPaymentSystem; // For EXPENSE or TRANSFER: debited payment system

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_payment_system_id")
    @Nullable
    private PaymentSystem toPaymentSystem; // For TRANSFER: credited payment system

    @Column(name = "user_id", nullable = false, updatable = false)
    private int userId;

}
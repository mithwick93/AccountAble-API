package org.mithwick93.accountable.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mithwick93.accountable.model.converter.CurrencyConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    @Nullable
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "category_id")
    @Nullable
    private Integer categoryId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "from_asset_id")
    @Nullable
    private Integer fromAssetId; // For TRANSFER: asset being transferred from

    @Column(name = "to_asset_id")
    @Nullable
    private Integer toAssetId; // For TRANSFER or INCOME: asset being transferred to

    @Column(name = "from_payment_system_id")
    @Nullable
    private Integer fromPaymentSystemId; // For EXPENSE or TRANSFER: debited payment system

    @Column(name = "to_payment_system_id")
    @Nullable
    private Integer toPaymentSystemId; // For TRANSFER: credited payment system

    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<SharedTransaction> sharedTransactions;

    public void setSharedTransactions(List<SharedTransaction> sharedTransactions) {
        if (this.sharedTransactions == null) {
            this.sharedTransactions = new ArrayList<>();
        }

        sharedTransactions.forEach(sharedTransaction -> sharedTransaction.setTransaction(this));

        this.sharedTransactions.clear();
        this.sharedTransactions.addAll(sharedTransactions);
    }

}
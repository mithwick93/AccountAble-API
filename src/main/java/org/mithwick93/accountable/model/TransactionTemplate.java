package org.mithwick93.accountable.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mithwick93.accountable.model.converter.CurrencyConverter;
import org.mithwick93.accountable.model.enums.Currency;
import org.mithwick93.accountable.model.enums.Frequency;
import org.mithwick93.accountable.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_templates")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTemplate extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "from_asset_id")
    @Nullable
    private Integer fromAssetId;

    @Column(name = "to_asset_id")
    @Nullable
    private Integer toAssetId;

    @Column(name = "from_payment_system_id")
    @Nullable
    private Integer fromPaymentSystemId;

    @Column(name = "to_payment_system_id")
    @Nullable
    private Integer toPaymentSystemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    @Nullable
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    @Nullable
    private DayOfWeek dayOfWeek;

    @Column(name = "day_of_month")
    @Nullable
    private Byte dayOfMonth;

    @Column(name = "month_of_year")
    @Nullable
    private Byte monthOfYear;

    @Column(name = "start_date")
    @Nullable
    private LocalDate startDate;

    @Column(name = "end_date")
    @Nullable
    private LocalDate endDate;

}

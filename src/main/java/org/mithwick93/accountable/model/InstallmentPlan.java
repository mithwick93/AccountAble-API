package org.mithwick93.accountable.model;

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

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "installment_plans")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentPlan extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "liability_id", nullable = false)
    private int liabilityId;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "installment_amount", nullable = false)
    private BigDecimal installmentAmount;

    @Column(name = "total_installments", nullable = false)
    private int totalInstallments;

    @Column(name = "installments_paid", nullable = false)
    private int installmentsPaid;

    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstallmentPlanStatus status;

}

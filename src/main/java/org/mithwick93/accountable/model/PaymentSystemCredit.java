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
import java.time.LocalDate;

@Entity
@DiscriminatorValue("2")
@Table(name = "payment_system_credits")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemCredit extends PaymentSystem {

    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;

    @Column(name = "utilized_amount")
    private BigDecimal utilizedAmount;

    @Column(name = "statement_date")
    @Nullable
    private LocalDate statementDate;

    @Column(name = "due_date")
    @Nullable
    private LocalDate dueDate;

}
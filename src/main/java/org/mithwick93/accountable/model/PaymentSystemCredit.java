package org.mithwick93.accountable.model;

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
@DiscriminatorValue("2")
@Table(name = "payment_system_credits")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemCredit extends PaymentSystem {

    @Column(name = "credit_limit", nullable = false, precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @Column(name = "utilized_amount", precision = 19, scale = 4)
    private BigDecimal utilizedAmount;

    @Column(name = "statement_day", nullable = false)
    private byte statementDay;

    @Column(name = "due_day", nullable = false)
    private byte dueDay;

}
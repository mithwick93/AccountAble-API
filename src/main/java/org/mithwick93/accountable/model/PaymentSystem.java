package org.mithwick93.accountable.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mithwick93.accountable.model.converter.CurrencyConverter;
import org.mithwick93.accountable.model.enums.Currency;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "payment_systems")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentSystem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    @Nullable
    private String description;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "security_code")
    private String securityCode;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "additional_note")
    private String additionalNote;

}

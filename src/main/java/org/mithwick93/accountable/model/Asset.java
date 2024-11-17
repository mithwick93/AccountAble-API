package org.mithwick93.accountable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mithwick93.accountable.model.converter.AssetTypeConverter;
import org.mithwick93.accountable.model.converter.CurrencyConverter;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = AssetTypeConverter.class)
    @Column(name = "type_id", nullable = false, updatable = false)
    private AssetType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal balance;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false, updatable = false)
    private Currency currency;

    @Column(name = "user_id", nullable = false, updatable = false)
    private int userId;
}

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
import org.mithwick93.accountable.model.enums.AssetType;
import org.mithwick93.accountable.model.enums.Currency;

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
    private Integer id;

    @Convert(converter = AssetTypeConverter.class)
    @Column(name = "type_id", nullable = false)
    private AssetType type;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Convert(converter = CurrencyConverter.class)
    @Column(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "active", nullable = false)
    private boolean active;

}

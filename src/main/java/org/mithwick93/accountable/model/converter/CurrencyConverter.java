package org.mithwick93.accountable.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mithwick93.accountable.model.Currency;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Currency currency) {
        if (currency == null) {
            return null;
        }
        return currency.getId();
    }

    @Override
    public Currency convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return Currency.fromId(id);
    }

}

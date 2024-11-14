package org.mithwick93.accountable.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mithwick93.accountable.model.PaymentSystemType;

@Converter(autoApply = true)
public class PaymentSystemTypeConverter implements AttributeConverter<PaymentSystemType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PaymentSystemType paymentSystemType) {
        if (paymentSystemType == null) {
            return null;
        }
        return paymentSystemType.getId();
    }

    @Override
    public PaymentSystemType convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return PaymentSystemType.fromId(id);
    }
}

package org.mithwick93.accountable.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mithwick93.accountable.model.enums.LiabilityType;

@Converter(autoApply = true)
public class LiabilityTypeConverter implements AttributeConverter<LiabilityType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LiabilityType liabilityType) {
        if (liabilityType == null) {
            return null;
        }
        return liabilityType.getId();
    }

    @Override
    public LiabilityType convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return LiabilityType.fromId(id);
    }

}

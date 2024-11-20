package org.mithwick93.accountable.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mithwick93.accountable.model.AssetType;

@Converter(autoApply = true)
public class AssetTypeConverter implements AttributeConverter<AssetType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AssetType assetType) {
        if (assetType == null) {
            return null;
        }
        return assetType.getId();
    }

    @Override
    public AssetType convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return AssetType.fromId(id);
    }

}

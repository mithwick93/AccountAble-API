package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.AssetRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.AssetTypeResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.AssetType;
import org.mithwick93.accountable.model.Currency;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AssetMapper {
    public abstract AssetResponse toAssetResponse(Asset asset);

    public abstract List<AssetResponse> toAssetResponses(List<Asset> assets);

    public abstract Asset toAsset(AssetRequest assetRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", expression = "java(assetType.toString())")
    public abstract AssetTypeResponse toAssetTypeResponse(AssetType assetType);

    public abstract List<AssetTypeResponse> toAssetTypeResponseList(List<AssetType> assetTypes);

    protected AssetType mapAssetTypeString(String type) {
        return AssetType.valueOf(type.toUpperCase());
    }

    protected Currency mapCurrencyString(String currency) {
        return Currency.valueOf(currency.toUpperCase());
    }

    protected String mapType(AssetType type) {
        return type.name();
    }

    protected String mapCurrency(Currency currency) {
        return currency.name();
    }
}

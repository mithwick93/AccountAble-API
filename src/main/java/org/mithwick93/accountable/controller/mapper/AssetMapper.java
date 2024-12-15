package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.AssetRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.AssetTypeResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.AssetType;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AssetMapper extends BaseMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Asset toAsset(AssetRequest assetRequest);

    @Mapping(target = "user", expression = "java(mapUser(asset.getUserId()))")
    public abstract AssetResponse toAssetResponse(Asset asset);

    public abstract List<AssetResponse> toAssetResponses(List<Asset> assets);

    @Mapping(target = "name", expression = "java(assetType.toString())")
    public abstract AssetTypeResponse toAssetTypeResponse(AssetType assetType);

    public abstract List<AssetTypeResponse> toAssetTypeResponses(List<AssetType> assetTypes);

}

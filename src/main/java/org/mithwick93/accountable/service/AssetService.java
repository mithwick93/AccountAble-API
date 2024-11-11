package org.mithwick93.accountable.service;

import org.mithwick93.accountable.model.Asset;

import java.util.List;

public interface AssetService {
    List<Asset> listAssets();

    Asset getAsset(Long assetId);

    Asset createAsset(Asset asset);

    Asset updateAsset(Long assetId, Asset updatedAsset);

    void deleteAsset(Long assetId);
}

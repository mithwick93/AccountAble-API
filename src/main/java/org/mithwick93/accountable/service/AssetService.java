package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetService {

    private final AssetRepository assetRepository;

    private final JwtUtil jwtUtil;

    public List<Asset> listAssets() {
        return assetRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    public Asset getAsset(int assetId) {
        return assetRepository.findByIdAndUserId(assetId, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Asset with id " + assetId + " not found"));
    }

    public Asset createAsset(Asset asset) {
        asset.setUserId(jwtUtil.getAuthenticatedUserId());
        return assetRepository.save(asset);
    }

    public Asset updateAsset(int assetId, Asset updatedAsset) {
        Asset existingAsset = getAsset(assetId);

        updatedAsset.setId(existingAsset.getId());
        updatedAsset.setUserId(existingAsset.getUserId());

        return assetRepository.save(updatedAsset);
    }

    public void deleteAsset(int assetId) {
        Asset existingAsset = getAsset(assetId);
        assetRepository.delete(existingAsset);
    }

}

package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AssetService(AssetRepository assetRepository, JwtUtil jwtUtil) {
        this.assetRepository = assetRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional(readOnly = true)
    public List<Asset> listAssets() {
        return assetRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public Asset getAsset(int assetId) {
        return assetRepository.findByIdAndUserId(assetId, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Asset with id " + assetId + " not found"));
    }

    @Transactional
    public Asset createAsset(Asset asset) {
        asset.setUserId(jwtUtil.getAuthenticatedUserId());
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset updateAsset(int assetId, Asset updatedAsset) {
        Asset existingAsset = getAsset(assetId);

        updatedAsset.setId(existingAsset.getId());
        updatedAsset.setUserId(existingAsset.getUserId());

        return assetRepository.save(updatedAsset);
    }

    @Transactional
    public void deleteAsset(int assetId) {
        Asset existingAsset = getAsset(assetId);
        assetRepository.delete(existingAsset);
    }
}

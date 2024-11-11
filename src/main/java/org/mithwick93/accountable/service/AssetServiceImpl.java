package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetServiceImpl extends BaseService implements AssetService {
    private final AssetRepository assetRepository;

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> listAssets() {
        return assetRepository.findAllByUserId(getAuthenticatedUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public Asset getAsset(Long assetId) {
        return assetRepository.findByIdAndUserId(assetId, getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Asset with id " + assetId + " not found"));
    }

    @Override
    @Transactional
    public Asset createAsset(Asset asset) {
        asset.setUserId(getAuthenticatedUserId());
        return assetRepository.save(asset);
    }

    @Override
    @Transactional
    public Asset updateAsset(Long assetId, Asset updatedAsset) {
        Asset existingAsset = getAsset(assetId);

        updatedAsset.setId(existingAsset.getId());
        updatedAsset.setUserId(existingAsset.getUserId());

        return assetRepository.save(updatedAsset);
    }

    @Override
    @Transactional
    public void deleteAsset(Long assetId) {
        Asset existingAsset = getAsset(assetId);
        assetRepository.delete(existingAsset);
    }
}

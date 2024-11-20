package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetService {

    private final AssetRepository assetRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Asset> getAll() {
        return assetRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public Asset getById(int id) {
        return assetRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Asset with id " + id + " not found"));
    }

    public Asset create(Asset asset) {
        asset.setUserId(jwtUtil.getAuthenticatedUserId());
        return assetRepository.save(asset);
    }

    public Asset update(int id, Asset updatedAsset) {
        Asset existingAsset = getById(id);

        updatedAsset.setId(existingAsset.getId());
        updatedAsset.setUserId(existingAsset.getUserId());

        return assetRepository.save(updatedAsset);
    }

    public void delete(int id) {
        Asset existingAsset = getById(id);
        assetRepository.delete(existingAsset);
    }

}

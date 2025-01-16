package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
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

    @CachePut(value = "asset_cache", key = "#result.id", unless = "#result == null")
    public Asset create(Asset asset) {
        asset.setUserId(jwtUtil.getAuthenticatedUserId());
        return assetRepository.save(asset);
    }

    @CachePut(value = "asset_cache", key = "#result.id", unless = "#result == null")
    public Asset update(int id, Asset asset) {
        Asset existingAsset = getById(id);

        existingAsset.setType(asset.getType());
        existingAsset.setName(asset.getName());
        existingAsset.setDescription(asset.getDescription());
        existingAsset.setBalance(asset.getBalance());
        existingAsset.setCurrency(asset.getCurrency());
        existingAsset.setActive(asset.isActive());

        return assetRepository.save(existingAsset);
    }

    @CachePut(value = "asset_cache", key = "#result.id", unless = "#result == null")
    public Asset updateBalance(int id, BigDecimal amount, Currency currency) {
        Asset existingAsset = getById(id);

        if (!existingAsset.getCurrency().equals(currency)) {
            throw new BadRequestException("Currency " + currency + " does not match asset currency " + existingAsset.getCurrency());
        }

        existingAsset.setBalance(existingAsset.getBalance().add(amount));

        return assetRepository.save(existingAsset);
    }

    @CacheEvict(value = "asset_cache", key = "#id")
    public void delete(int id) {
        Asset existingAsset = getById(id);
        assetRepository.delete(existingAsset);
    }

}

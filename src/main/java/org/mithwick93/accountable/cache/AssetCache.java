package org.mithwick93.accountable.cache;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.AssetRepository;
import org.mithwick93.accountable.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetCache {

    private final AssetRepository assetRepository;

    @Cacheable(value = "asset_cache", key = "#id", unless = "#result == null")
    public Asset getAsset(int id) {
        return assetRepository.findById(id)
                .orElse(null);
    }

}

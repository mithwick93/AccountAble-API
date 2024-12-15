package org.mithwick93.accountable.cache;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.LiabilityRepository;
import org.mithwick93.accountable.model.Liability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LiabilityCache {

    private final LiabilityRepository liabilityRepository;

    @Cacheable(value = "liability_cache", key = "#id", unless = "#result == null")
    public Liability getLiability(int id) {
        return liabilityRepository.findById(id)
                .orElse(null);
    }

}


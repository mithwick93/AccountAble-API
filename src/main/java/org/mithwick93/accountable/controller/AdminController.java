package org.mithwick93.accountable.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AdminController {

    private final CacheManager cacheManager;

    @DeleteMapping(path = "/cache/invalidate", produces = {"application/json"})
    public ResponseEntity<Void> invalidateAll() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(this::getCacheAndClear);

        return ResponseEntity.noContent().build();
    }

    private void getCacheAndClear(final String cacheName) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresentOrElse(
                        cache -> {
                            log.info("Clearing cache: {}", cacheName);
                            cache.clear();
                        },
                        () -> log.error("Invalid cache name: {}", cacheName)
                );
    }

}

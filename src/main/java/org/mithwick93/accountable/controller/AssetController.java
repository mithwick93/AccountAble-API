package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.AssetRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.AssetTypeResponse;
import org.mithwick93.accountable.controller.mapper.AssetMapper;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.enums.AssetType;
import org.mithwick93.accountable.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetController {

    private final AssetService assetService;

    private final AssetMapper assetMapper;

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAll() {
        List<Asset> assets = assetService.getAll();
        List<AssetResponse> assetResponses = assetMapper.toAssetResponses(assets);
        return ResponseEntity.ok(assetResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getById(@PathVariable int id) {
        Asset asset = assetService.getById(id);
        AssetResponse assetResponse = assetMapper.toAssetResponse(asset);
        return ResponseEntity.ok(assetResponse);
    }

    @PostMapping
    public ResponseEntity<AssetResponse> create(@Valid @RequestBody AssetRequest request) {
        Asset newAsset = assetMapper.toAsset(request);
        Asset createdAsset = assetService.create(newAsset);
        AssetResponse assetResponse = assetMapper.toAssetResponse(createdAsset);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> update(
            @PathVariable int id,
            @Valid @RequestBody AssetRequest request
    ) {
        Asset updateAsset = assetMapper.toAsset(request);
        Asset updatedAsset = assetService.update(id, updateAsset);
        AssetResponse updatedAssetResponse = assetMapper.toAssetResponse(updatedAsset);
        return ResponseEntity.ok(updatedAssetResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        assetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<AssetTypeResponse>> getTypes() {
        List<AssetType> assetTypes = Arrays.asList(AssetType.values());
        List<AssetTypeResponse> assetTypeResponses = assetMapper.toAssetTypeResponses(assetTypes);
        return ResponseEntity.ok(assetTypeResponses);
    }

}

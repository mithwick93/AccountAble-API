package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import org.mithwick93.accountable.controller.dto.request.AssetRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.AssetTypeResponse;
import org.mithwick93.accountable.controller.mapper.AssetMapper;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.AssetType;
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
public class AssetController {

    private final AssetService assetService;
    private final AssetMapper assetMapper;

    @Autowired
    public AssetController(AssetService assetService, AssetMapper assetMapper) {
        this.assetService = assetService;
        this.assetMapper = assetMapper;
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> listAssets() {
        List<Asset> assets = assetService.listAssets();
        List<AssetResponse> assetResponses = assetMapper.toAssetResponses(assets);
        return ResponseEntity.ok(assetResponses);
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<AssetResponse> getAsset(@PathVariable Long assetId) {
        Asset asset = assetService.getAsset(assetId);
        AssetResponse assetResponse = assetMapper.toAssetResponse(asset);
        return ResponseEntity.ok(assetResponse);
    }

    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@Valid @RequestBody AssetRequest newAssetRequest) {
        Asset newAsset = assetMapper.toAsset(newAssetRequest);
        Asset createdAsset = assetService.createAsset(newAsset);
        AssetResponse assetResponse = assetMapper.toAssetResponse(createdAsset);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetResponse);
    }

    @PutMapping("/{assetId}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable Long assetId,
            @Valid @RequestBody AssetRequest updateAssetRequest
    ) {
        Asset updateAsset = assetMapper.toAsset(updateAssetRequest);
        Asset updatedAsset = assetService.updateAsset(assetId, updateAsset);
        AssetResponse updatedAssetResponse = assetMapper.toAssetResponse(updatedAsset);
        return ResponseEntity.ok(updatedAssetResponse);
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long assetId) {
        assetService.deleteAsset(assetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<AssetTypeResponse>> getAssetTypes() {
        List<AssetType> assetTypes = Arrays.asList(AssetType.values());
        List<AssetTypeResponse> assetTypeResponseList = assetMapper.toAssetTypeResponseList(assetTypes);
        return ResponseEntity.ok(assetTypeResponseList);
    }
}

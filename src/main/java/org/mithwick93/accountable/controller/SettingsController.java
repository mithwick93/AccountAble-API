package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.SettingRequest;
import org.mithwick93.accountable.controller.dto.response.SettingResponse;
import org.mithwick93.accountable.controller.mapper.SettingMapper;
import org.mithwick93.accountable.model.Setting;
import org.mithwick93.accountable.service.SettingsService;
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

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsController {

    private final SettingsService settingsService;

    private final SettingMapper settingMapper;

    @GetMapping
    public ResponseEntity<List<SettingResponse>> getAll() {
        List<Setting> settings = settingsService.getAll();
        List<SettingResponse> settingResponses = settingMapper.toSettingResponses(settings);
        return ResponseEntity.ok(settingResponses);
    }

    @GetMapping("/{settingKey}")
    public ResponseEntity<SettingResponse> getById(@PathVariable String settingKey) {
        Setting setting = settingsService.getByKey(settingKey);
        SettingResponse settingResponse = settingMapper.toSettingResponse(setting);
        return ResponseEntity.ok(settingResponse);
    }

    @PostMapping
    public ResponseEntity<SettingResponse> create(@Valid @RequestBody SettingRequest request) {
        Setting newSetting = settingMapper.toSetting(request);
        Setting createdSetting = settingsService.save(newSetting);
        SettingResponse settingResponse = settingMapper.toSettingResponse(createdSetting);
        return ResponseEntity.status(HttpStatus.CREATED).body(settingResponse);
    }

    @PutMapping
    public ResponseEntity<SettingResponse> update(@Valid @RequestBody SettingRequest request) {
        Setting updateSetting = settingMapper.toSetting(request);
        Setting updatedSetting = settingsService.update(updateSetting);
        SettingResponse settingResponse = settingMapper.toSettingResponse(updatedSetting);
        return ResponseEntity.ok(settingResponse);
    }

    @DeleteMapping("/{settingKey}")
    public ResponseEntity<Void> delete(@PathVariable String settingKey) {
        settingsService.delete(settingKey);
        return ResponseEntity.noContent().build();
    }

}

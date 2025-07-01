package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.SettingsRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Setting;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsService {

    private final JwtUtil jwtUtil;

    private final SettingsRepository settingsRepository;

    @Transactional(readOnly = true)
    public List<Setting> getAll() {
        return settingsRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public Setting getByKey(String settingKey) {
        return settingsRepository.findBySettingKeyAndUserId(settingKey, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Setting with key " + settingKey + " not found"));
    }

    public Setting save(Setting setting) {
        if (settingsRepository.existsBySettingKeyAndUserId(setting.getSettingKey(), jwtUtil.getAuthenticatedUserId())) {
            return update(setting);
        }

        setting.setUserId(jwtUtil.getAuthenticatedUserId());
        return settingsRepository.save(setting);
    }

    public Setting update(Setting setting) {
        Setting existingSetting = getByKey(setting.getSettingKey());
        existingSetting.setSettingValue(setting.getSettingValue());
        return settingsRepository.save(existingSetting);
    }

    public void delete(String settingKey) {
        Setting existingSetting = getByKey(settingKey);
        settingsRepository.delete(existingSetting);
    }

}

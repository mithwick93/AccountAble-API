package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Long> {

    List<Setting> findAllByUserId(int userId);

    Optional<Setting> findBySettingKeyAndUserId(String settingKey, int userId);

    boolean existsBySettingKeyAndUserId(String settingKey, int userId);

}

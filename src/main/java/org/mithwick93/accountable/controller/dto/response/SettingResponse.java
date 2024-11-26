package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record SettingResponse(
        long id,
        String settingKey,
        String settingValue,
        int userId,
        LocalDateTime created,
        LocalDateTime modified
) {

}

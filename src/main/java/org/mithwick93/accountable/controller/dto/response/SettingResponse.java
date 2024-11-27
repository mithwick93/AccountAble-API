package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record SettingResponse(
        long id,
        String settingKey,
        String settingValue,
        UserResponse user,
        LocalDateTime created,
        LocalDateTime modified
) {

}

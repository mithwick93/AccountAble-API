package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.validation.ValidJson;

public record SettingRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        String settingKey,

        @NotBlank
        @Size(min = 1, max = 10000)
        @ValidJson
        String settingValue
) {

}

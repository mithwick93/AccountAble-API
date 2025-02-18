package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.enums.AssetType;
import org.mithwick93.accountable.model.enums.Currency;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;

public record AssetRequest(
        @NotBlank
        @ValidEnum(enumClass = AssetType.class, message = "must be a valid asset type")
        String type,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 500)
        String description,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal balance,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        Boolean active
) {

}
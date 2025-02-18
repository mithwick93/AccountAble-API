package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.enums.Currency;
import org.mithwick93.accountable.validation.ValidEnum;

public record PaymentSystemCreditRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        int liabilityId,

        Boolean active,

        String cardHolderName,

        String cardNumber,

        String securityCode,

        String expiryDate,

        String additionalNote
) {

}
package org.mithwick93.accountable.controller.dto.response;

import java.time.LocalDateTime;

public record PaymentSystemCreditResponse(
        int id,
        String name,
        String description,
        String currency,
        LiabilityResponse liability,
        UserResponse user,
        boolean active,
        String cardHolderName,
        String cardNumber,
        String securityCode,
        String expiryDate,
        String additionalNote,
        LocalDateTime created,
        LocalDateTime modified
) {

}

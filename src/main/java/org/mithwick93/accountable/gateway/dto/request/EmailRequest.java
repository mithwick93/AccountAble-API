package org.mithwick93.accountable.gateway.dto.request;

import jakarta.annotation.Nullable;

public record EmailRequest(
        String recipientName,
        String recipientEmail,
        String subject,
        @Nullable String textContent,
        @Nullable String htmlContent
) {

}

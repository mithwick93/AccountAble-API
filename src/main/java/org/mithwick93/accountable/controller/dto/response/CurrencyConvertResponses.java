package org.mithwick93.accountable.controller.dto.response;

import java.util.List;

public record CurrencyConvertResponses(
        List<CurrencyConvertResponse> responses
) {

    public static CurrencyConvertResponses of(List<CurrencyConvertResponse> responses) {
        return new CurrencyConvertResponses(responses);
    }

}

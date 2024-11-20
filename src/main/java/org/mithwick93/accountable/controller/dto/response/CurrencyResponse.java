package org.mithwick93.accountable.controller.dto.response;

public record CurrencyResponse(
        int id,
        String code,
        String name,
        String description
) {

}

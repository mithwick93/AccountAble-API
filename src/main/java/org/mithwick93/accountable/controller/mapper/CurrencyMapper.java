package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.response.CurrencyResponse;
import org.mithwick93.accountable.model.Currency;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CurrencyMapper {
    @Mapping(target = "code", expression = "java(currency.toString())")
    public abstract CurrencyResponse toCurrencyResponse(Currency currency);

    public abstract List<CurrencyResponse> toCurrencyResponseList(List<Currency> currencies);
}

package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.response.ExchangeRateResponse;

import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class ExchangeRateMapper {

    @Mapping(target = "conversionRates", source = "conversionRates")
    public abstract ExchangeRateResponse toExchangeRateResponse(Map<String, Double> conversionRates);

}

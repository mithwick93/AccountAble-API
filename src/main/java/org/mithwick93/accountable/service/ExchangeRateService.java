package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.gateway.CurrencyConversionGateway;
import org.mithwick93.accountable.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeRateService {

    private static final Set<String> SUPPORTED_CURRENCY_CODES = Arrays.stream(Currency.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    private final CurrencyConversionGateway currencyConversionGateway;

    public Map<String, Double> getCurrencyExchangeRates(boolean onlySupported) {
        Map<String, Double> exchangeRates = currencyConversionGateway.getExchangeRates();

        if (!onlySupported) {
            return exchangeRates;
        }

        return exchangeRates.entrySet().stream()
                .filter(entry -> SUPPORTED_CURRENCY_CODES.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}

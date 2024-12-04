package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.gateway.CurrencyConversionGateway;
import org.mithwick93.accountable.gateway.GoldConversionGateway;
import org.mithwick93.accountable.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeRateService {

    private static final Set<String> SUPPORTED_CURRENCY_CODES = Arrays.stream(Currency.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    private static final int DECIMAL_PRECISION = 8;

    private final CurrencyConversionGateway currencyConversionGateway;

    private final GoldConversionGateway goldConversionGateway;

    private static double formatToFourDecimalPlaces(double value) {
        return BigDecimal.valueOf(value)
                .setScale(DECIMAL_PRECISION, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static Map<String, Double> getFilteredAndFormatedRates(
            boolean onlySupported,
            Map<String, Double> exchangeRates
    ) {
        return exchangeRates.entrySet().stream()
                .filter(entry -> !onlySupported || SUPPORTED_CURRENCY_CODES.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> formatToFourDecimalPlaces(entry.getValue())
                ));
    }

    public Map<String, Double> getExchangeRates(
            Currency baseCurrency,
            boolean onlySupported
    ) {
        Map<String, Double> exchangeRates = getExchangeRates(baseCurrency);
        return getFilteredAndFormatedRates(onlySupported, exchangeRates);
    }

    private Map<String, Double> getExchangeRates(Currency baseCurrency) {
        Map<String, Double> currencyRates = currencyConversionGateway.getExchangeRates();
        Map<String, Double> goldRateMap = getGoldExchangeRates(currencyRates);

        Map<String, Double> exchangeRates = new HashMap<>(currencyRates);
        exchangeRates.putAll(goldRateMap);

        Double baseCurrencyRate = exchangeRates.get(baseCurrency.name());
        if (baseCurrencyRate == null || baseCurrencyRate == 0) {
            throw new IllegalArgumentException("Invalid or unsupported base currency: " + baseCurrency.name() + " rate: " + baseCurrencyRate);
        }

        double conversionFactor = 1 / baseCurrencyRate;
        return exchangeRates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * conversionFactor
                ));
    }

    private Map<String, Double> getGoldExchangeRates(Map<String, Double> currencyRates) {
        return Optional.ofNullable(goldConversionGateway.getGoldRate().get(Currency.G24.name()))
                .map(goldRates -> goldRates.get(Currency.LKR.name()))
                .map(goldRate -> currencyRates.get(Currency.LKR.name()) / goldRate)
                .map(formattedRate -> Map.of(Currency.G24.name(), formattedRate))
                .orElse(Map.of());
    }

}

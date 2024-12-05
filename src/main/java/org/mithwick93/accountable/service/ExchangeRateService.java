package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.CurrencyConvertRequests;
import org.mithwick93.accountable.controller.dto.response.CurrencyConvertResponse;
import org.mithwick93.accountable.controller.dto.response.CurrencyConvertResponses;
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

    private static BigDecimal formatToFourDecimalPlaces(BigDecimal value) {
        return value.setScale(DECIMAL_PRECISION, RoundingMode.HALF_UP);
    }

    private static double formatToFourDecimalPlaces(double value) {
        return formatToFourDecimalPlaces(BigDecimal.valueOf(value)).doubleValue();
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

    private Map<String, Double> getExchangeRates() {
        return getExchangeRates(Currency.USD, false);
    }

    public CurrencyConvertResponses convertCurrency(CurrencyConvertRequests requests) {
        Map<String, Double> exchangeRates = getExchangeRates();

        return CurrencyConvertResponses.of(
                requests.payload().stream()
                        .map(request -> {
                            BigDecimal sourceRate = BigDecimal.valueOf(getValidatedRate(exchangeRates, request.sourceCurrency()));
                            BigDecimal targetRate = BigDecimal.valueOf(getValidatedRate(exchangeRates, request.targetCurrency()));
                            BigDecimal sourceAmount = request.sourceAmount();
                            BigDecimal conversionRate = targetRate.divide(sourceRate, RoundingMode.HALF_UP);
                            BigDecimal targetAmount = formatToFourDecimalPlaces(
                                    sourceAmount.multiply(conversionRate)
                            );

                            return new CurrencyConvertResponse(
                                    request.sourceCurrency(),
                                    request.targetCurrency(),
                                    sourceAmount,
                                    targetAmount,
                                    formatToFourDecimalPlaces(conversionRate).doubleValue()
                            );
                        })
                        .toList()
        );
    }

    private Map<String, Double> getExchangeRates(Currency baseCurrency) {
        Map<String, Double> currencyRates = currencyConversionGateway.getExchangeRates();
        Map<String, Double> goldRateMap = getGoldExchangeRates(currencyRates);

        Map<String, Double> exchangeRates = new HashMap<>(currencyRates);
        exchangeRates.putAll(goldRateMap);

        Double baseCurrencyRate = getValidatedRate(exchangeRates, baseCurrency.name());

        double conversionFactor = 1 / baseCurrencyRate;
        return exchangeRates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * conversionFactor
                ));
    }

    private Map<String, Double> getGoldExchangeRates(Map<String, Double> currencyRates) {
        return goldConversionGateway.getGoldExchangeRate()
                .map(rate -> getValidatedRate(currencyRates, Currency.LKR.name()) / rate)
                .map(rate -> Map.of(Currency.G24.name(), rate))
                .orElse(Map.of());
    }

    private Double getValidatedRate(Map<String, Double> currencyRates, String currencyCode) {
        Double value = currencyRates.get(currencyCode);

        if (value == null || value == 0) {
            throw new IllegalArgumentException("Invalid or unsupported currency: " + currencyCode + " rate: " + value);
        }

        return value;
    }

}

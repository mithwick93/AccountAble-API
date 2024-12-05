package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.CurrencyConvertRequests;
import org.mithwick93.accountable.controller.dto.response.CurrencyConvertResponses;
import org.mithwick93.accountable.controller.dto.response.ExchangeRateResponse;
import org.mithwick93.accountable.controller.mapper.ExchangeRateMapper;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    private final ExchangeRateMapper exchangeRateMapper;

    @GetMapping
    public ResponseEntity<ExchangeRateResponse> getAll(
            @RequestParam(name = "baseCurrency", required = false, defaultValue = "USD") Currency baseCurrency,
            @RequestParam(name = "onlySupported", required = false, defaultValue = "true") boolean onlySupported
    ) {
        Map<String, Double> exchangeRates = exchangeRateService.getExchangeRates(baseCurrency, onlySupported);
        ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.toExchangeRateResponse(exchangeRates);
        return ResponseEntity.ok(exchangeRateResponse);
    }

    @PostMapping("/convert")
    public ResponseEntity<CurrencyConvertResponses> convertCurrency(
            @Valid @RequestBody CurrencyConvertRequests requests
    ) {
        CurrencyConvertResponses currencyConvertResponses = exchangeRateService.convertCurrency(requests);
        return ResponseEntity.ok(currencyConvertResponses);
    }

}

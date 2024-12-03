package org.mithwick93.accountable.controller;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.response.ExchangeRateResponse;
import org.mithwick93.accountable.controller.mapper.ExchangeRateMapper;
import org.mithwick93.accountable.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam(name = "onlySupported", required = false, defaultValue = "false") boolean onlySupported
    ) {
        Map<String, Double> currencyExchangeRates = exchangeRateService.getCurrencyExchangeRates(onlySupported);
        ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.toExchangeRateResponse(currencyExchangeRates);
        return ResponseEntity.ok(exchangeRateResponse);
    }

}

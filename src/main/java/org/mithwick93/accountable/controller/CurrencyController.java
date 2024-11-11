package org.mithwick93.accountable.controller;

import org.mithwick93.accountable.controller.dto.response.CurrencyResponse;
import org.mithwick93.accountable.controller.mapper.CurrencyMapper;
import org.mithwick93.accountable.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyMapper currencyMapper;

    @Autowired
    public CurrencyController(CurrencyMapper currencyMapper) {
        this.currencyMapper = currencyMapper;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getCurrencies() {
        List<Currency> currencies = Arrays.asList(Currency.values());
        List<CurrencyResponse> currencyResponseList = currencyMapper.toCurrencyResponseList(currencies);
        return ResponseEntity.ok(currencyResponseList);
    }
}

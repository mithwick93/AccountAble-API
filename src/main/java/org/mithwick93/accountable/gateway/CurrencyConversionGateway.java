package org.mithwick93.accountable.gateway;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.exception.ExternalServiceException;
import org.mithwick93.accountable.gateway.dto.response.CurrencyConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyConversionGateway {

    private final RestClient restClient;

    @Value("${currency.api.url}")
    private String apiUrl;

    @Value("${currency.api.key}")
    private String apiKey;

    @Value("${currency.api.base:USD}")
    private String baseCurrency;

    @Cacheable(value = "exchange_rate_cache", unless = "#result == null")
    public Map<String, Double> getExchangeRates() {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(apiUrl)
                    .build(apiKey, baseCurrency);
            CurrencyConversionResponse response = restClient
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(CurrencyConversionResponse.class);

            if (response == null || !"success".equalsIgnoreCase(response.getResult())) {
                throw new ExternalServiceException("Failed to fetch exchange rates: " + response);
            }

            return response.getConversionRates();
        } catch (Exception e) {
            throw new ExternalServiceException(
                    "Currency Conversion Service error: " + e.getMessage()
            );
        }
    }

}

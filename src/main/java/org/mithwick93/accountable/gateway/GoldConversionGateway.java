package org.mithwick93.accountable.gateway;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.gateway.helper.GoldPriceExtractor;
import org.mithwick93.accountable.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoldConversionGateway {

    private final GoldPriceExtractor goldPriceExtractor;

    public Map<String, Map<String, Double>> getGoldRate() {
        return Optional.ofNullable(goldPriceExtractor.extractGoldRate())
                .map(rate -> Map.of(
                        Currency.G24.name(), Map.of(Currency.LKR.name(), rate))
                )
                .orElse(Map.of());
    }

}

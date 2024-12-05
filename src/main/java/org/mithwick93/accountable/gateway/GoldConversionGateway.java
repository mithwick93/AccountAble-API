package org.mithwick93.accountable.gateway;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.gateway.helper.GoldPriceExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoldConversionGateway {

    private final GoldPriceExtractor goldPriceExtractor;

    public Optional<Double> getGoldExchangeRate() {
        return Optional.ofNullable(goldPriceExtractor.extractGoldRate());
    }

}

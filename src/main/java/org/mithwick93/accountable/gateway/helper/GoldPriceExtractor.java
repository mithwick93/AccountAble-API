package org.mithwick93.accountable.gateway.helper;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoldPriceExtractor {

    @Value("${metal.gold.url}")
    private String url;

    @Value("${metal.gold.xpath}")
    private String xpath;

    @Value("${spring.web.client.connect-timeout}")
    private int connectTimeout;

    @Cacheable(value = "gold_rate_cache", unless = "#result == null")
    @Nullable
    public Double extractGoldRate() {
        try {
            Elements elements = Jsoup.connect(url)
                    .timeout(connectTimeout)
                    .get()
                    .selectXpath(xpath);

            if (elements.isEmpty()) {
                return null;
            }

            return Double.parseDouble(elements
                    .getFirst()
                    .text()
                    .substring(4)
                    .replace(",", "")
            );
        } catch (Exception e) {
            log.error("Error when extracting gold price from {}", url, e);
            return null;
        }
    }

}

package org.mithwick93.accountable.gateway.helper;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class GoldPriceExtractor {

    private static final Pattern DIGITS_ONLY_PATTERN = Pattern.compile("\\D");

    @Value("${metal.gold.url}")
    private String url;

    @Value("${metal.gold.xpath}")
    private String xpath;

    @Cacheable(value = "gold_rate_cache", unless = "#result == null")
    @Nullable
    public Double extractGoldRate() {
        try {
            Elements elements = Jsoup.connect(url)
                    .get()
                    .selectXpath(xpath);
            Matcher matcher = DIGITS_ONLY_PATTERN.matcher(elements.text());
            return Double.parseDouble(matcher.replaceAll(""));
        } catch (Exception e) {
            log.error("Error when extracting gold price from {}", url, e);
            return null;
        }
    }

}

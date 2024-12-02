package org.mithwick93.accountable.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${spring.web.client.connect-timeout}")
    private int connectTimeout;

    @Value("${spring.web.client.read-timeout}")
    private int readTimeout;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(customRequestFactory())
                .build();
    }

    ClientHttpRequestFactory customRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofMillis(connectTimeout))
                .withReadTimeout(Duration.ofMillis(readTimeout));
        return ClientHttpRequestFactories.get(settings);
    }

}

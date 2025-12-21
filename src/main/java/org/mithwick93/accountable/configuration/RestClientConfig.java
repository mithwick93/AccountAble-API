package org.mithwick93.accountable.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${spring.web.client.connect-timeout}")
    private Duration connectTimeout;

    @Value("${spring.web.client.read-timeout}")
    private Duration readTimeout;

    @Bean
    public RestClient restClient() {

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .build();

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory(httpClient);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().add("X-Read-Timeout",
                            readTimeout.toString());
                    return execution.execute(request, body);
                })
                .build();
    }

}

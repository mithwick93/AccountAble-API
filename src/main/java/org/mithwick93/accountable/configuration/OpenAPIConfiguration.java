package org.mithwick93.accountable.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application-title}") String title,
            @Value("${application-description}") String description,
            @Value("${application-version}") String version
    ) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(title)
                                .version(version)
                                .description(description)
                                .termsOfService("http://swagger.io/terms/")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("https://springdoc.org/v2/")
                                )
                );

    }
}
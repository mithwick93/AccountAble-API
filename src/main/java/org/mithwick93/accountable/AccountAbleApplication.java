package org.mithwick93.accountable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AccountAbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountAbleApplication.class, args);
    }

}

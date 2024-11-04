package org.mithwick93.accountable;

import org.springframework.boot.SpringApplication;

public class TestAccountAbleApplication {

    public static void main(String[] args) {
        SpringApplication.from(AccountAbleApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

package org.mithwick93.accountable.exception;

import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public static Supplier<BadRequestException> supplier(String message) {
        return () -> new BadRequestException(message);
    }

}

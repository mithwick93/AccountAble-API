package org.mithwick93.accountable.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static Supplier<NotFoundException> supplier(String message) {
        return () -> new NotFoundException(message);
    }
}
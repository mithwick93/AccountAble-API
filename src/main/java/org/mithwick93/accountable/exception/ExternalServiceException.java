package org.mithwick93.accountable.exception;

import java.util.function.Supplier;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    public static Supplier<ExternalServiceException> supplier(String message) {
        return () -> new ExternalServiceException(message);
    }

}
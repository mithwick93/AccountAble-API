package org.mithwick93.accountable.exception;

import java.util.function.Supplier;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public static Supplier<AuthException> supplier(String message) {
        return () -> new AuthException(message);
    }
}

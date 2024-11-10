package org.mithwick93.accountable.exception;

import java.util.function.Supplier;

public class AuthException extends RuntimeException {
    public static Supplier<AuthException> supplier(String message) {
        return () -> new AuthException(message);
    }

    public AuthException(String message) {
        super(message);
    }
}

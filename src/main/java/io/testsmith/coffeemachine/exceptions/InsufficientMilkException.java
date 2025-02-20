package io.testsmith.coffeemachine.exceptions;

public class InsufficientMilkException extends RuntimeException {
    public InsufficientMilkException(String message) {
        super(message);
    }
}
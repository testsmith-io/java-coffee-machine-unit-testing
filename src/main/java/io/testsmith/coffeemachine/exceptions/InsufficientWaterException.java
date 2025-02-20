package io.testsmith.coffeemachine.exceptions;

public class InsufficientWaterException extends RuntimeException {
    public InsufficientWaterException(String message) {
        super(message);
    }
}
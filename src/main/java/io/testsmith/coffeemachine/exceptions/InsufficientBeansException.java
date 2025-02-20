package io.testsmith.coffeemachine.exceptions;

public class InsufficientBeansException extends RuntimeException {
    public InsufficientBeansException(String message) {
        super(message);
    }
}
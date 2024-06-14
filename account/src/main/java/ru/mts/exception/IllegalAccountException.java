package ru.mts.exception;

public class IllegalAccountException extends RuntimeException {

    public IllegalAccountException(String message) {
        super(message);
    }
}

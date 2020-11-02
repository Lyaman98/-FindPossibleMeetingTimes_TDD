package com.java.tdd.exceptions;

public class CalendarIsEmptyException extends RuntimeException {
    public CalendarIsEmptyException(String message) {
        super(message);
    }
}

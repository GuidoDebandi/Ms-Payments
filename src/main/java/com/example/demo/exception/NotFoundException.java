package com.example.demo.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, RuntimeException e) {
        super(msg + " because of " + e.toString());
    }
}


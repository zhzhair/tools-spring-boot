package com.example.demo.common.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(int code, String message) {
        super(code + "|" + message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}

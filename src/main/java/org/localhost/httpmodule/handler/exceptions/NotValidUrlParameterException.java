package org.localhost.httpmodule.handler.exceptions;

public class NotValidUrlParameterException extends RuntimeException {
    public NotValidUrlParameterException(String message) {
        super(message);
    }
}

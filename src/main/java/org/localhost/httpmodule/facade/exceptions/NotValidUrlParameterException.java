package org.localhost.httpmodule.facade.exceptions;

public class NotValidUrlParameterException extends RuntimeException {
    public NotValidUrlParameterException(String message) {
        super(message);
    }
}

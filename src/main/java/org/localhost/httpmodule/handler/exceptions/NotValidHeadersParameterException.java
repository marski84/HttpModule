package org.localhost.httpmodule.handler.exceptions;

public class NotValidHeadersParameterException extends RuntimeException {
    public NotValidHeadersParameterException(String message) {
        super(message);
    }
}

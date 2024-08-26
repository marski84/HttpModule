package org.localhost.httpmodule.facade.exceptions;

public class NotValidHeadersParameterException extends RuntimeException {
    public NotValidHeadersParameterException(String message) {
        super(message);
    }
}

package org.localhost.httpmodule.handler.exceptions;

public class NotValidBodyParameterException extends RuntimeException {
    public NotValidBodyParameterException(String message) {
        super(message);
    }
}

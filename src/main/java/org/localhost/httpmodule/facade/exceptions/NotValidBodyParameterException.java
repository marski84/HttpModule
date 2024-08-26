package org.localhost.httpmodule.facade.exceptions;

public class NotValidBodyParameterException extends RuntimeException {
    public NotValidBodyParameterException(String message) {
        super(message);
    }
}

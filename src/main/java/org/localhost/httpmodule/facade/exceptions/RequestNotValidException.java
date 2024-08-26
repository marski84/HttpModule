package org.localhost.httpmodule.facade.exceptions;

public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException(String requestCannotBeNull) {
        super(requestCannotBeNull);
    }
}

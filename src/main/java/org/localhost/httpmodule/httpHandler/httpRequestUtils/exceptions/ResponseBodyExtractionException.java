package org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions;

public class ResponseBodyExtractionException extends Exception {
    public ResponseBodyExtractionException(String message) {
        super(message);
    }

    public ResponseBodyExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}


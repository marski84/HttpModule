package org.localhost.httpmodule.handler.RequestBuilder;

import okhttp3.*;
import org.localhost.httpmodule.handler.exceptions.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestBuilder {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    private Request.Builder requestBuilder;


    public RequestBuilder createRequestBuilder(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new NotValidUrlParameterException("URL is null or empty");
        }
        this.requestBuilder = new Request.Builder().url(url);
        return this;
    }


    RequestBuilder addHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            throw new NotValidHeadersParameterException("headers is null or empty");
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        this.requestBuilder.headers(headersBuilder.build());

        return this;
    }

    RequestBuilder addJsonRequestBody(String body, String method) {
        if (body == null || body.isEmpty()) {
            throw new NotValidBodyParameterException("body is null or empty");
        }
        if (method == null || method.isEmpty()) {
            throw new NotValidMethodException("Not a valid request method!");
        }
        if (method.equals(GET) || method.equals(DELETE)) {
            throw new NotValidMethodException("Not a valid request method to add body!!");
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json"),
                body
        );
         switch (method) {
            case POST -> requestBuilder.post(reqBody);
            case PUT -> requestBuilder.put(reqBody);
            case PATCH -> requestBuilder.patch(reqBody);
            default -> throw new NoSuchHttpMethodException();
        };

        return this;
    }

    public RequestBuilder createRequestWithoutBody(String method) {
        if (method == null || method.isEmpty()) {
            throw new NotValidMethodException("Not a valid request method!");
        }

        if (method.equals(PATCH) || method.equals(POST) || method.equals(PUT)) {
            throw new NotValidMethodException("Not a valid request method for method without body!");
        }
        switch (method) {
            case GET -> requestBuilder.get();
            case DELETE -> requestBuilder.delete();
        }

        return this;
    }

    public Request build() {
        if (this.requestBuilder == null) {
            throw new IllegalStateException("Request builder has not been initialized. Call createRequestBuilder first.");
        }
        return this.requestBuilder.build();
    }
}
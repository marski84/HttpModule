package org.localhost.httpmodule.facade.RequestBuilder.RequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import okhttp3.*;
import org.localhost.httpmodule.facade.exceptions.*;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Getter
public class RequestBuilderImpl implements RequestBuilder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";

    // Zmień typ na Request.Builder
    private Request.Builder requestBuilder;

    @Override
    public RequestBuilder createRequestBuilder(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new NotValidUrlParameterException("URL is null or empty");
        }

        URL validUrl = createUrl(url);

        this.requestBuilder = new Request.Builder().url(validUrl);
        return this;
    }

    @Override
    public RequestBuilder addHeaders(Map<String, String> headers) {
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

    @Override
    public RequestBuilder addJsonRequestBody(String body, String method) {
        if (body == null || body.isEmpty()) {
            throw new NotValidBodyParameterException("body is null or empty");
        }
        if (method == null || method.isEmpty()) {
            throw new NotValidMethodException("Not a valid request method!");
        }
        if (method.equals(GET) || method.equals(DELETE)) {
            throw new NotValidMethodException("Not a valid request method to add body!!");
        }

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new NotValidBodyParameterException("Failed to serialize body to JSON");
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json"),
                jsonBody
        );

        switch (method) {
            case POST -> this.requestBuilder.post(reqBody);
            case PUT -> this.requestBuilder.put(reqBody);
            case PATCH -> this.requestBuilder.patch(reqBody);
            default -> throw new NoSuchHttpMethodException();
        }

        return this;
    }

    @Override
    public RequestBuilder createRequestWithoutBody(String method) {
        if (method == null || method.isEmpty()) {
            throw new NotValidMethodException("Not a valid request method!");
        }

        if (method.equals(PATCH) || method.equals(POST) || method.equals(PUT)) {
            throw new NotValidMethodException("Not a valid request method for method without body!");
        }
        switch (method) {
            case GET -> this.requestBuilder.get();
            case DELETE -> this.requestBuilder.delete();
            default -> throw new NotValidMethodException("Unsupported method: " + method);
        }

        return this;
    }

    @Override
    public Request build() {
        if (this.requestBuilder == null) {
            throw new IllegalStateException("Request builder has not been initialized. Call createRequestBuilder first.");
        }
        return this.requestBuilder.build();
    }

    public URL createUrl(String urlString) throws UrlCreationException {
        try {
            return new URI(urlString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new UrlCreationException("Invalid URL: " + urlString, e);
        }
    }
}
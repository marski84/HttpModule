package org.localhost.httpmodule.httpHandler.httpRequestUtils;

import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.NoSuchHttpMethodException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.NotValidParameterException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.PayloadInitFailedException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.UrlCreationException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public final class HttpRequestFactory {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    private HttpRequestFactory() {
    }

    public static HttpUriRequest createRequestForUrl(String url, String method) throws UrlCreationException {
        Objects.requireNonNull(url);
        Objects.requireNonNull(method);
        URL urlAddress = createUrl(url);
        return switch (method) {
            case GET -> new HttpGet(urlAddress.toString());
            case POST -> new HttpPost(urlAddress.toString());
            case PUT -> new HttpPut(urlAddress.toString());
            case PATCH -> new HttpPatch(urlAddress.toString());
            case DELETE -> new HttpDelete(urlAddress.toString());
            default -> throw new NoSuchHttpMethodException();
        };
    }

    public static HttpUriRequest addHttpHeaders(HttpUriRequest request, List<Header> headers) {
        try {
            Objects.requireNonNull(request, "Request cannot be null");
            Objects.requireNonNull(headers, "Headers cannot be null");
        } catch (NullPointerException e) {
            throw new NotValidParameterException("Not valid parameter!", e);
        }

        if (headers.isEmpty()) {
            throw new IllegalArgumentException("Headers cannot be empty");
        }
        request.setHeaders(headers.toArray(new Header[headers.size()]));
        return request;
    }

    public static HttpUriRequest addPayload(HttpUriRequest request, String body) throws PayloadInitFailedException {
        try {
            Objects.requireNonNull(request, "Request cannot be null");
            Objects.requireNonNull(body, "Body cannot be null");
        } catch (NullPointerException e) {
            throw new NotValidParameterException("Not valid parameter!", e);
        }

        if (!(request instanceof HttpEntityEnclosingRequestBase entityRequest)) {
            throw new PayloadInitFailedException("Request payload init failed!");
        }

        StringEntity requestPayload = new StringEntity(body, StandardCharsets.UTF_8);
        requestPayload.setContentType("application/json");
        entityRequest.setEntity(requestPayload);

        entityRequest.setHeader("Accept", "application/json");
        entityRequest.setHeader("Content-type", "application/json");

        return entityRequest;
    }


    private static URL createUrl(String urlString) throws UrlCreationException {
        try {
            return new URI(urlString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new UrlCreationException("Invalid URL: " + urlString, e);
        }
    }


}

package org.localhost.httpmodule.httpRequestUtils;

import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.localhost.httpmodule.exceptions.HttpMethodNotSuitableForPayloadException;
import org.localhost.httpmodule.exceptions.NoSuchHttpMethodException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class HttpRequestFactory {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    private HttpRequestFactory() {
    }

    public static HttpUriRequest createRequestForUrl(String url, String method) {
        URL urlAddress = getUrl(url);
        return switch (method) {
            case GET -> new HttpGet(urlAddress.toString());
            case POST -> new HttpPost(urlAddress.toString());
            case PUT -> new HttpPut(urlAddress.toString());
            case PATCH -> new HttpPatch(urlAddress.toString());
            case DELETE -> new HttpDelete(urlAddress.toString());
            default -> throw new NoSuchHttpMethodException();
        };
    }

    public static HttpUriRequest addHttpHeaders(HttpUriRequest request, Header[] headers) {
        Objects.requireNonNull(request, "Request cannot be null");
        Objects.requireNonNull(headers, "Headers cannot be null");
        request.setHeaders(headers);
        return request;
    }

    public static HttpUriRequest addPayload(HttpUriRequest request, String body) {
        Objects.requireNonNull(request, "Request cannot be null");
        Objects.requireNonNull(body, "Body cannot be null");

        if (!(request instanceof HttpEntityEnclosingRequestBase entityRequest)) {
            throw new RuntimeException();
        }

        StringEntity requestPayload = new StringEntity(body, StandardCharsets.UTF_8);
        requestPayload.setContentType("application/json");
        entityRequest.setEntity(requestPayload);

        entityRequest.setHeader("Accept", "application/json");
        entityRequest.setHeader("Content-type", "application/json");

        return entityRequest;
    }


    private static URL getUrl(String url) {
        URL urlAddress = null;
        try {
            URI uri = new URI(url);
            urlAddress = uri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return urlAddress;
    }


}

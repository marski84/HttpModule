package org.localhost.httpmodule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.localhost.httpmodule.httpRequestUtils.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;


//@Service
public class HttpHandlerImpl implements HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpHandlerImpl.class);
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public CloseableHttpResponse get(String url, Header[] headers) {
        HttpUriRequest getRequest = createSimpleRequest(url, headers, HttpRequestFactory.GET);
        return executeRequest(getRequest);
    }

    @Override
    public CloseableHttpResponse delete(String url, Header[] headers) {
        HttpUriRequest deleteRequest = createSimpleRequest(url, headers, HttpRequestFactory.DELETE);
        return executeRequest(deleteRequest);
    }

    @Override
    public CloseableHttpResponse post(String url, Header[] headers, String body) {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.POST, body);
        return executeRequest(postRequest);
    }

    @Override
    public CloseableHttpResponse put(String url, Header[] headers, String body) {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PUT, body);
        return executeRequest(postRequest);    }

    @Override
    public CloseableHttpResponse patch(String url, Header[] headers, String body) {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PATCH, body);
        return executeRequest(postRequest);
    }


    private CloseableHttpResponse executeRequest(HttpUriRequest request) {
        Objects.requireNonNull(request, "request cannot be null");
//        try (CloseableHttpResponse response = httpClient.execute(request)) {
//            System.out.println("ogin");
//                return response;
//        }
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            System.out.println("ogin");
            System.out.println("response: " + response);
            return response;
        }
        catch (IOException e) {
            throw new RuntimeException("Http request execution failed", e);
        }
    }


    private HttpUriRequest createSimpleRequest(String url, Header[] headers, String method) {
        Objects.requireNonNull(url, "url cannot be null");
        HttpUriRequest request = HttpRequestFactory.createRequestForUrl(url, method);
        if (headers != null) {
            for (Header header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }
        }
        return request;
    }

    private HttpUriRequest createRequestWithPayload(
            String url,
            Header[] headers,
            String method,
            String body
    ) {
        Objects.requireNonNull(url, "url cannot be null");
        HttpUriRequest request = HttpRequestFactory.createRequestForUrl(url, method);
        if (headers != null) {
            for (Header header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }
        }
        if (body != null) {
            HttpRequestFactory.addPayload(request, body);
        }
        return request;
    }

}
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

import java.io.IOException;
import java.util.Objects;


//@Service
public class HttpHandlerImpl implements HttpHandler {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode get(String url, Header[] headers) {
        HttpUriRequest getRequest = createSimpleRequest(url, headers, HttpRequestFactory.GET);
        return executeRequest(getRequest);
    }

    @Override
    public JsonNode delete(String url, Header[] headers) {
        HttpUriRequest deleteRequest = createSimpleRequest(url, headers, HttpRequestFactory.DELETE);
        return executeRequest(deleteRequest);
    }

    @Override
    public JsonNode post(String url, Header[] headers, String body) {
        Objects.requireNonNull(url, "url cannot be null");
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.POST, body);
        return executeRequest(postRequest);
    }

    @Override
    public JsonNode put(String url, Header[] headers, String body) {
        Objects.requireNonNull(url, "url cannot be null");
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PUT, body);
        return executeRequest(postRequest);    }

    @Override
    public JsonNode patch(String url, Header[] headers, String body) {
        Objects.requireNonNull(url, "url cannot be null");
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PATCH, body);
        return executeRequest(postRequest);
    }


    private JsonNode executeRequest(HttpUriRequest request) {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String jsonString = EntityUtils.toString(entity);

                System.out.println(objectMapper.readTree(jsonString));
                return objectMapper.readTree(jsonString);
            }
        } catch (IOException e) {
            throw new RuntimeException("Http request execution failed", e);
        }
        return null;
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
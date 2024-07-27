package org.localhost.httpmodule.httpHandler;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.RequestFailedException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.UrlCreationException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.HttpRequestFactory;
import java.io.IOException;
import java.util.Objects;


public class HttpHandlerImpl implements HttpHandler {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public CloseableHttpResponse get(String url, Header[] headers) throws RequestFailedException, UrlCreationException {
        HttpUriRequest getRequest = createSimpleRequest(url, headers, HttpRequestFactory.GET);
        return executeRequest(getRequest);
    }

    @Override
    public CloseableHttpResponse delete(String url, Header[] headers) throws RequestFailedException, UrlCreationException {
        HttpUriRequest deleteRequest = createSimpleRequest(url, headers, HttpRequestFactory.DELETE);
        return executeRequest(deleteRequest);
    }

    @Override
    public CloseableHttpResponse post(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.POST, body);
        return executeRequest(postRequest);
    }

    @Override
    public CloseableHttpResponse put(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PUT, body);
        return executeRequest(postRequest);    }

    @Override
    public CloseableHttpResponse patch(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, HttpRequestFactory.PATCH, body);
        return executeRequest(postRequest);
    }


    private CloseableHttpResponse executeRequest(HttpUriRequest request) throws RequestFailedException {
        Objects.requireNonNull(request, "request cannot be null");
        try {
            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RequestFailedException("Http request execution failed");
        }
    }

    private HttpUriRequest createSimpleRequest(String url, Header[] headers, String method) throws UrlCreationException {
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
    ) throws UrlCreationException {
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
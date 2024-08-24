package org.localhost.httpmodule.handler;
import okhttp3.OkHttpClient;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.localhost.httpmodule.handler.exceptions.RequestFailedException;
import org.localhost.httpmodule.handler.exceptions.UrlCreationException;
import java.io.IOException;
import java.util.Objects;


public class HttpHandlerImpl implements HttpHandler {

    @Override
    public CloseableHttpResponse get(String url, Header[] headers) throws RequestFailedException, UrlCreationException {
        HttpUriRequest getRequest = createSimpleRequest(url, headers, "");
        return executeRequest(getRequest);
    }

    @Override
    public CloseableHttpResponse delete(String url, Header[] headers) throws RequestFailedException, UrlCreationException {
        HttpUriRequest deleteRequest = createSimpleRequest(url, headers, "");
        return executeRequest(deleteRequest);
    }

    @Override
    public CloseableHttpResponse post(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, "HttpRequestFactory.POST", body);
        return executeRequest(postRequest);
    }

    @Override
    public CloseableHttpResponse put(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, "HttpRequestFactory.PUT", body);
        return executeRequest(postRequest);    }

    @Override
    public CloseableHttpResponse patch(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException {
        HttpUriRequest postRequest = createRequestWithPayload(url, headers, "HttpRequestFactory.PATCH", body);
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

        return null;
    }

    private HttpUriRequest createRequestWithPayload(
            String url,
            Header[] headers,
            String method,
            String body
    ) throws UrlCreationException {
        Objects.requireNonNull(url, "url cannot be null");

        return null;
    }

}
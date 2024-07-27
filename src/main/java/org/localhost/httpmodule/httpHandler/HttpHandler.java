package org.localhost.httpmodule.httpHandler;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.RequestFailedException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.UrlCreationException;

public interface HttpHandler {

    CloseableHttpResponse get(String url, Header[] headers) throws RequestFailedException, UrlCreationException;

    CloseableHttpResponse post(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException;
    CloseableHttpResponse put(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException;
    CloseableHttpResponse patch(String url, Header[] headers, String body) throws RequestFailedException, UrlCreationException;
    CloseableHttpResponse delete(String url, Header[] headers) throws RequestFailedException, UrlCreationException;
}

package org.localhost.httpmodule.httpHandler;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

public interface HttpHandler {

    CloseableHttpResponse get(String url, Header[] headers);

    CloseableHttpResponse post(String url, Header[] headers, String body);
    CloseableHttpResponse put(String url, Header[] headers, String body);
    CloseableHttpResponse patch(String url, Header[] headers, String body);
    CloseableHttpResponse delete(String url, Header[] headers);
}

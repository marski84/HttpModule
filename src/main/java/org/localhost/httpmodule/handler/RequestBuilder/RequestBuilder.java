package org.localhost.httpmodule.handler.RequestBuilder;

import okhttp3.Request;

import java.net.URL;
import java.util.Map;

public interface RequestBuilder {
    RequestBuilder addHeaders(Map<String, String> headers);
    RequestBuilder addJsonRequestBody(String body, String method);
    RequestBuilder createRequestWithoutBody(String method);
    Request build();
    URL createUrl(String urlString);
}

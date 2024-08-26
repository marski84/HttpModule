package org.localhost.httpmodule.facade.service;

import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

public interface HttpService {
    Request createGetRequest(String url);
    Request createGetRequest(String url, Map<String, String> headers);

    Request createDeleteRequest(String url);
    Request createDeleteRequest(String url, Map<String, String> headers);

    Request createPatchRequest(String url, String body);
    Request createPatchRequest(String url, String body, Map<String, String> headers);

    Request createPostRequest(String url, String body);
    Request createPostRequest(String url, String body, Map<String, String> headers);

    Request createPutRequest(String url, String body);
    Request createPutRequest(String url, String body, Map<String, String> headers);

    Response executeRequest(Request request);
}

package org.localhost.httpmodule.facade;

import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

public interface HttpHandlerFacade {
    Response handleGetRequest(String url);
    Response handleGetRequest(String url, Map<String, String> headers);
    Response handleDeleteRequest(String url);
    Response handleDeleteRequest(String url, Map<String, String> headers);
    Response handlePatchRequest(String url, String body);
    Response handlePatchRequest(String url, String body, Map<String, String> headers);
    Response handlePostRequest(String url, String body);
    Response handlePostRequest(String url, String body, Map<String, String> headers);
    Response handlePutRequest(String url, String body);
    Response handlePutRequest(String url, String body, Map<String, String> headers);
}

package org.localhost.httpmodule.facade;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.localhost.httpmodule.facade.service.HttpService;
import org.springframework.stereotype.Controller;
import java.util.Map;

@Controller
public class HttpHandlerFacadeImpl implements HttpHandlerFacade {
    private final HttpService httpService;
    public final OkHttpClient client;


    public HttpHandlerFacadeImpl(HttpService httpService, OkHttpClient client) {
        this.httpService = httpService;
        this.client = client;
    }

    @Override
    public Response handleGetRequest(String url) {
        Request request = httpService.createGetRequest(url);
        return httpService.executeRequest(request);
    }



    @Override
    public Response handleGetRequest(String url, Map<String, String> headers) {
        Request request = httpService.createGetRequest(url, headers);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handleDeleteRequest(String url) {
        Request request = httpService.createDeleteRequest(url);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handleDeleteRequest(String url, Map<String, String> headers) {
        Request request = httpService.createDeleteRequest(url, headers);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handlePatchRequest(String url, String body) {
        Request request = httpService.createPatchRequest(url, body);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handlePatchRequest(String url, String body, Map<String, String> headers) {
        Request request = httpService.createPatchRequest(url, body, headers);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handlePostRequest(String url, String body) {
        Request request = httpService.createPostRequest(url, body);
        return httpService.executeRequest(request);    }

    @Override
    public Response handlePostRequest(String url, String body, Map<String, String> headers) {
        Request request = httpService.createPostRequest(url, body, headers);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handlePutRequest(String url, String body) {
        Request request = httpService.createPutRequest(url, body);
        return httpService.executeRequest(request);
    }

    @Override
    public Response handlePutRequest(String url, String body, Map<String, String> headers) {
        Request request = httpService.createPutRequest(url, body, headers);
        return httpService.executeRequest(request);
    }
}

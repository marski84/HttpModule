package org.localhost.httpmodule.facade.service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.localhost.httpmodule.facade.RequestBuilder.RequestBuilder.RequestBuilderImpl;
import org.localhost.httpmodule.facade.exceptions.NotValidParameterException;
import org.localhost.httpmodule.facade.exceptions.RequestNotValidException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class HttpServiceImpl implements HttpService {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    private final OkHttpClient client;

    public HttpServiceImpl(OkHttpClient client) {
        this.client = client;
    }


    @Override
    public Request createGetRequest(String url) {
        validateInputUrl(url);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .createRequestWithoutBody(GET)
                .build();
    }

    @Override
    public Request createGetRequest(String url, Map<String, String> headers) {
        validateInputUrl(url);
        validateInputHeader(headers);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .createRequestWithoutBody(GET)
                .addHeaders(headers)
                .build();
    }

    @Override
    public Request createDeleteRequest(String url) {
        validateInputUrl(url);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .createRequestWithoutBody(DELETE)
                .build();
    }

    @Override
    public Request createDeleteRequest(String url, Map<String, String> headers) {
        validateInputUrl(url);
        validateInputHeader(headers);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .createRequestWithoutBody(DELETE)
                .addHeaders(headers)
                .build();
    }

    @Override
    public Request createPatchRequest(String url, String body) {
        validateInputUrl(url);
        validateInputBody(body);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, PATCH)
                .build();
    }

    @Override
    public Request createPatchRequest(String url, String body, Map<String, String> headers) {
        validateInputUrl(url);
        validateInputBody(body);
        validateInputHeader(headers);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, PATCH)
                .addHeaders(headers)
                .build();
    }

    @Override
    public Request createPostRequest(String url, String body) {
        validateInputUrl(url);
        validateInputBody(body);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, POST)
                .build();
    }

    @Override
    public Request createPostRequest(String url, String body, Map<String, String> headers) {
        validateInputUrl(url);
        validateInputBody(body);
        validateInputHeader(headers);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, POST)
                .addHeaders(headers)
                .build();
    }

    @Override
    public Request createPutRequest(String url, String body) {
        validateInputUrl(url);
        validateInputBody(body);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, PUT)
                .build();
    }

    @Override
    public Request createPutRequest(String url, String body, Map<String, String> headers) {
        validateInputUrl(url);
        validateInputBody(body);
        validateInputHeader(headers);
        return new RequestBuilderImpl()
                .createRequestBuilder(url)
                .addJsonRequestBody(body, PUT)
                .addHeaders(headers)
                .build();
    }


    private void validateInputUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new NotValidParameterException("URL cannot be null or empty!");
        }
    }

    private void validateInputBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new NotValidParameterException("Request body cannot be null or empty!");
        }
    }

    private void validateInputHeader(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            throw new NotValidParameterException("Request headers cannot be null or empty!");
        }
    }

    public Response executeRequest(Request request) {
        if (request == null) {
            throw new RequestNotValidException("Request cannot be null");
        }
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute request", e);
        }
    }
}

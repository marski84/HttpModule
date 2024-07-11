package org.localhost.httpmodule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


//@Service
public class HttpHandlerImpl implements HttpHandler {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode get(String url) {
        URL urlAddress = getUrl(url);
        HttpUriRequest request = createRequest(urlAddress, null, MethodType.GET);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return executeRequest(request);
    }

    @Override
    public JsonNode post(String url, String body) {
        URL urlAddress = getUrl(url);
        HttpUriRequest request = createRequest(urlAddress, body, MethodType.POST);
        return executeRequest(request);
    }

    @Override
    public JsonNode put(String url, String body) {
        URL urlAddress = getUrl(url);
        HttpUriRequest request = createRequest(urlAddress, body, MethodType.PUT);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return executeRequest(request);
    }

    @Override
    public JsonNode patch(String url, String body) {
        URL urlAddress = getUrl(url);
        HttpUriRequest request = createRequest(urlAddress, body, MethodType.PATCH);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return executeRequest(request);
    }

    @Override
    public JsonNode delete(String url) {
        URL urlAddress = getUrl(url);
        HttpUriRequest request = createRequest(urlAddress, null, MethodType.DELETE);
        return executeRequest(request);

    }

    private URL getUrl(String url) {
        URL urlAddress = null;
        try {
            URI uri = new URI(url);
            urlAddress = uri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return urlAddress;
    }

    private HttpUriRequest createRequest(URL urlAddress, String body, MethodType requestMethod) {
        HttpUriRequest request = null;

        switch (requestMethod) {
            case GET -> request = createGetRequest(urlAddress);
            case POST -> request =createPostRequest(urlAddress, body);
            case PUT -> request = createPutRequest(urlAddress, body);
            case PATCH -> request = createPatchRequest(urlAddress, body);
            case DELETE -> request = createDeleteRequest(urlAddress);
        }
        return request;
    }

    private HttpPost createPostRequest(URL urlAddress, String body) {
        HttpPost request = new HttpPost(urlAddress.toString());
        StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }

    private HttpPut createPutRequest(URL urlAddress, String body) {
        HttpPut request = new HttpPut(urlAddress.toString());
        StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }

    private HttpUriRequest createDeleteRequest(URL urlAddress) {
        HttpDelete request = new HttpDelete(urlAddress.toString());
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }

    private HttpUriRequest createPatchRequest(URL urlAddress, String body) {
        HttpPatch request = new HttpPatch(urlAddress.toString());
        StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }

    private HttpUriRequest createGetRequest(URL urlAddress) {
        HttpUriRequest request = new HttpGet(urlAddress.toString());
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
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

    private HttpUriRequest setRequestHeaders(HttpUriRequest request) {
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }

}

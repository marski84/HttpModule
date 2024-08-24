package org.localhost.httpmodule.httpRequestUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.httpmodule.handler.exceptions.NoSuchHttpMethodException;
import org.localhost.httpmodule.handler.exceptions.NotValidParameterException;
import org.localhost.httpmodule.handler.exceptions.UrlCreationException;
import org.localhost.httpmodule.handler.httpRequestUtils.HttpRequestFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestFactoryTest {

    HttpUriRequest createRequest(String url, String method) throws UrlCreationException {
        //        given, when
        HttpUriRequest result = HttpRequestFactory.createRequestForUrl(url, method);
//        then
        assertNotNull(result);
        assertEquals(url, result.getURI().toString());
        assertEquals(method, result.getRequestLine().getMethod());

        return result;
    }

    @Test
    @DisplayName("It should create valid GET request")
    void createGetRequestForUrl() throws UrlCreationException {
        createRequest("http://localhost/", HttpRequestFactory.GET);
    }

    @Test
    @DisplayName("It should create valid POST request")
    void createPostRequestForUrl() throws UrlCreationException {
        createRequest("http://localhost/", HttpRequestFactory.POST);
    }

    @Test
    @DisplayName("It should create valid PUT request")
    void createPutRequestForUrl() throws UrlCreationException {
        createRequest("http://localhost/", HttpRequestFactory.PUT);
    }

    @Test
    @DisplayName("It should create valid PATCH request")
    void createPatchRequestForUrl() throws UrlCreationException {
        createRequest("http://localhost/", HttpRequestFactory.PATCH);
    }

    @Test
    @DisplayName("It should create valid DELETE request")
    void createDeleteRequestForUrl() throws UrlCreationException {
        createRequest("http://localhost/", HttpRequestFactory.DELETE);

    }

    @Test
    void shouldThrowWhenUrlIsNull() {
        assertThrows(NullPointerException.class, () -> HttpRequestFactory.createRequestForUrl(null, HttpRequestFactory.GET));
        assertThrows(NullPointerException.class, () -> HttpRequestFactory.createRequestForUrl(null, HttpRequestFactory.POST));
        assertThrows(NullPointerException.class, () -> HttpRequestFactory.createRequestForUrl(null, HttpRequestFactory.PATCH));
        assertThrows(NullPointerException.class, () -> HttpRequestFactory.createRequestForUrl(null, HttpRequestFactory.PUT));
        assertThrows(NullPointerException.class, () -> HttpRequestFactory.createRequestForUrl(null, HttpRequestFactory.DELETE));
    }

    @Test
    void shouldThrowWhenMethodIsNotValid() {
        assertThrows(
                NoSuchHttpMethodException.class,
                () -> HttpRequestFactory.createRequestForUrl("http://localhost/resource", "invalidMethod")
        );
    }

    @Test
    void addHttpHeaders() throws UrlCreationException {
//        given
        HttpUriRequest getRequest = createRequest("http://localhost/", HttpRequestFactory.GET);
        HttpUriRequest postRequest = createRequest("http://localhost/", HttpRequestFactory.POST);
        HttpUriRequest putRequest = createRequest("http://localhost/", HttpRequestFactory.PUT);
        HttpUriRequest patchRequest = createRequest("http://localhost/", HttpRequestFactory.PATCH);
        HttpUriRequest deleteRequest = createRequest("http://localhost/", HttpRequestFactory.DELETE);
//        when
        List<HttpUriRequest> requests = List.of(
                getRequest, postRequest, putRequest, patchRequest, deleteRequest
        );
        final Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        List<Header> headers = List.of(header);
//        then
        requests.forEach(req -> HttpRequestFactory.addHttpHeaders(req, headers));
        requests.forEach(req -> assertEquals(header, req.getHeaders("Content-Type")[0]));
    }

    @Test
    @DisplayName("addHeaders should throw when request is null")
    void shouldThrowWhenRequestIsNull() {
        assertThrows(NotValidParameterException.class, () -> {
            HttpRequestFactory.addHttpHeaders(null, List.of());
        });
    }

    @Test
    @DisplayName("addHeaders should throw when header is null")
    void shouldThrowWhenHeaderIsNull() throws UrlCreationException {
        HttpUriRequest getRequest = createRequest("http://localhost/", HttpRequestFactory.GET);

        assertThrows(NotValidParameterException.class, () -> {
            HttpRequestFactory.addHttpHeaders(getRequest, null);
        });
    }

    @Test
    @DisplayName("It should add given payload to request")
    void addPayload() throws IOException, UrlCreationException {
        // given
        HttpUriRequest postRequest = createRequest("http://localhost/", HttpRequestFactory.POST);
        HttpUriRequest putRequest = createRequest("http://localhost/", HttpRequestFactory.PUT);
        HttpUriRequest patchRequest = createRequest("http://localhost/", HttpRequestFactory.PATCH);

        // when
        List<HttpUriRequest> requests = List.of(postRequest, putRequest, patchRequest);
        final String payload = "testPayload";

        // then
        requests.forEach(req -> HttpRequestFactory.addPayload(req, payload));

        for (HttpUriRequest request : requests) {
            if (request instanceof HttpEntityEnclosingRequest entityRequest) {
                HttpEntity entity = entityRequest.getEntity();
                String actualPayload = EntityUtils.toString(entity);
                assertEquals(payload, actualPayload);
            }
        }
    }

    @Test
    @DisplayName("It should throw when given payload is null")
    void addPayloadShouldThrowWhenPayloadIsNull() throws UrlCreationException {
//        given, when, then
        HttpUriRequest postRequest = createRequest("http://localhost/", HttpRequestFactory.POST);
        assertThrows(NotValidParameterException.class, () -> HttpRequestFactory.addPayload(postRequest, null));
    }

    @Test
    @DisplayName("It should throw when given payload is null")
    void addPayloadShouldThrowWhenUrlIsNull() {
//        given, when, then
        final String payload = "testPayload";
        assertThrows(NotValidParameterException.class, () -> HttpRequestFactory.addPayload(null,payload ));

    }
}
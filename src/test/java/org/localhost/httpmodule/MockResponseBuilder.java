package org.localhost.httpmodule;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MockResponseBuilder {
    private int statusCode = 200;
    private String reasonPhrase = "OK";
    private HttpVersion httpVersion = HttpVersion.HTTP_1_1;
    private List<Header> headers = new ArrayList<>();
    private String body = "";

    public MockResponseBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public MockResponseBuilder setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    public MockResponseBuilder addHeader(String name, String value) {
        this.headers.add(new BasicHeader(name, value));
        return this;
    }

    public MockResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public CloseableHttpResponse build() throws IOException {
        CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);

        // Set status line
        StatusLine statusLine = new BasicStatusLine(httpVersion, statusCode, reasonPhrase);
        Mockito.when(response.getStatusLine()).thenReturn(statusLine);

        // Set headers
        Mockito.when(response.getAllHeaders()).thenReturn(headers.toArray(new Header[0]));

        // Set body
        HttpEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        Mockito.when(response.getEntity()).thenReturn(entity);

        return response;
    }
}

package org.localhost.httpmodule.httpHandler.httpUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.ResponseBodyExtractionException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class HttpUtils {
    private HttpUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getResponseBodyAsJson(CloseableHttpResponse httpResponse) throws JsonProcessingException, ResponseBodyExtractionException {
        Objects.requireNonNull(httpResponse);
        String responseBody = getResponseBody(httpResponse);
        return objectMapper.readTree(responseBody);
    }

    public static String getResponseBody(CloseableHttpResponse httpResponse) throws ResponseBodyExtractionException {
        Objects.requireNonNull(httpResponse, "HTTP response cannot be null");

        try (httpResponse) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity == null) {
                throw new ResponseBodyExtractionException("Response entity is null");
            }
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException | ResponseBodyExtractionException e) {
            throw new ResponseBodyExtractionException("Failed to read response body", e);
        }
    }

}

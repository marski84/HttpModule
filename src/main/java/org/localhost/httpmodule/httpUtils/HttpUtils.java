package org.localhost.httpmodule.httpUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

public final class HttpUtils {
    private HttpUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getResponseBodyAsJson(CloseableHttpResponse httpResponse) throws JsonProcessingException {
        Objects.requireNonNull(httpResponse);
        String responseBody = getResponseBody(httpResponse);
        return objectMapper.readTree(responseBody);
    }

    public static String getResponseBody(CloseableHttpResponse httpResponse) {
        Objects.requireNonNull(httpResponse);
        String result = null;
        try {
            HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity, "UTF-8");
            result = response;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

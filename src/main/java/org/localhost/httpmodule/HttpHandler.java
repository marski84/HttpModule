package org.localhost.httpmodule;

import com.fasterxml.jackson.databind.JsonNode;

public interface HttpHandler {

    JsonNode get(String url);

     JsonNode post(String url, String body);
     JsonNode put(String url, String body);
     JsonNode patch(String url, String body);
     JsonNode delete(String url);
}

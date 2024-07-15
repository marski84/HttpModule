package org.localhost.httpmodule;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.Header;

public interface HttpHandler {

    JsonNode get(String url, Header[] headers);

     JsonNode post(String url, Header[] headers, String body);
     JsonNode put(String url, Header[] headers, String body);
     JsonNode patch(String url, Header[] headers, String body);
     JsonNode delete(String url, Header[] headers);
}

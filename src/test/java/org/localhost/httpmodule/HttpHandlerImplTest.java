package org.localhost.httpmodule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.junit.jupiter.api.*;
import org.localhost.httpmodule.httpHandler.HttpHandlerImpl;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.RequestFailedException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.ResponseBodyExtractionException;
import org.localhost.httpmodule.httpHandler.httpRequestUtils.exceptions.UrlCreationException;
import org.localhost.httpmodule.httpHandler.httpUtils.HttpUtils;

import java.io.IOException;




class HttpHandlerImplTest {

//    test config
    private final HttpHandlerImpl objectUnderTest = new HttpHandlerImpl();
    public static MockWebServer mockBackEnd;
    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

//    end of test config



    @Test
    @DisplayName("It should make a successful get request")
    void getRequestTest() throws JsonProcessingException, RequestFailedException, UrlCreationException, ResponseBodyExtractionException {
        String expectedBody = "{\"id\":\"1\",\"name\":\"Google Pixel 6 Pro\",\"data\":{\"color\":\"Cloudy White\",\"capacity\":\"128 GB\"}}";
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(expectedBody);

        mockBackEnd.enqueue(response);
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
//        when
         CloseableHttpResponse testResult = objectUnderTest.get(baseUrl, null);
         String actualBody = HttpUtils.getResponseBody(testResult);
//         then
        Assertions.assertEquals("HTTP/1.1 200 OK", String.valueOf(testResult.getStatusLine()));
        Assertions.assertEquals(response.getHeaders().get("Content-Type"), String.valueOf(testResult.getEntity().getContentType().getValue()));

        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);

        Assertions.assertEquals(expectedJson, actualJson);

    }

    @Test
    @DisplayName("It should perform successful delete request")
    void deleteRequestTest() throws JsonProcessingException, RequestFailedException, UrlCreationException, ResponseBodyExtractionException {
        String expectedBody = """
                   "message": "Object with id = 6, has been deleted."
                }""";
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(expectedBody);

        mockBackEnd.enqueue(response);
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
//        when
        CloseableHttpResponse testResult = objectUnderTest.delete(baseUrl + "/6", null);
        String actualBody = HttpUtils.getResponseBody(testResult);
//         then
        Assertions.assertEquals("HTTP/1.1 200 OK", String.valueOf(testResult.getStatusLine()));
        Assertions.assertEquals(response.getHeaders().get("Content-Type"), String.valueOf(testResult.getEntity().getContentType().getValue()));

        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("it should perform successful post request")
    void postRequestTest() throws IOException, RequestFailedException, UrlCreationException, ResponseBodyExtractionException {
        String expectedBody = """
                {
                   "id": "7",
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 1849.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB"
                   },
                   "createdAt": "2022-11-21T20:06:23.986Z"
                }""";

        String postReqBody = """
                {
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 1849.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB"
                   }
                }""";
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Location", baseUrl + "/product/10")
                .setBody(expectedBody);

        mockBackEnd.enqueue(response);

//        when
        CloseableHttpResponse testResult = objectUnderTest.post(baseUrl, null, postReqBody);
        String actualBody = HttpUtils.getResponseBody(testResult);
        //         then

        Assertions.assertEquals("HTTP/1.1 200 OK", String.valueOf(testResult.getStatusLine()));
        Assertions.assertEquals(response.getHeaders().get("Content-Type"), String.valueOf(testResult.getEntity().getContentType().getValue()));

        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);
        System.out.println(response.getHeaders().get("Location"));

        Assertions.assertEquals(expectedJson, actualJson);

        String expectedLocationHeader = response.getHeaders().get("Location");
        String actualLocationHeader = testResult.getHeaders("Location")[0].getValue();
        Assertions.assertEquals(
                expectedLocationHeader,
                actualLocationHeader
                );


    }

    @Test
    @DisplayName("It should perform successful put request")
    void putRequestTest() throws JsonProcessingException, RequestFailedException, UrlCreationException, ResponseBodyExtractionException {
        String expectedBody = """
                {
                   "id": "7",
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 2049.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB",
                      "color": "silver"
                   },
                   "updatedAt": "2022-12-25T21:08:41.986Z"
                }

                """;

        String putReqBody = """
                {
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 2049.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB",
                      "color": "silver"
                   }
                }""";
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Location", baseUrl + "/product/7")
                .setBody(expectedBody);

        mockBackEnd.enqueue(response);

//        when
        CloseableHttpResponse testResult = objectUnderTest.post(baseUrl, null, putReqBody);
        String actualBody = HttpUtils.getResponseBody(testResult);
        //         then
        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);
        System.out.println(response.getHeaders().get("Location"));

        Assertions.assertEquals(expectedJson, actualJson);

        String expectedLocationHeader = response.getHeaders().get("Location");
        String actualLocationHeader = testResult.getHeaders("Location")[0].getValue();
        Assertions.assertEquals(
                expectedLocationHeader,
                actualLocationHeader
        );


    }

    @Test
    @DisplayName("It should perform successful patch request")
    void patchRequestTest() throws JsonProcessingException, RequestFailedException, UrlCreationException, ResponseBodyExtractionException {
        String expectedBody = """
                {
                   "id": "7",
                   "name": "Apple MacBook Pro 16 (Updated Name)",
                   "data": {
                      "year": 2019,
                      "price": 1849.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB"
                   },
                   "updatedAt": "2022-12-25T21:09:46.986Z"
                }""";

        String patchReqBody = """
                {
                   "name": "Apple MacBook Pro 16 (Updated Name)"
                }""";

        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Location", baseUrl + "/product/7")
                .setBody(expectedBody);

        mockBackEnd.enqueue(response);

//        when
        CloseableHttpResponse testResult = objectUnderTest.patch(baseUrl, null, patchReqBody);
        String actualBody = HttpUtils.getResponseBody(testResult);
        //         then
        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);
        System.out.println(response.getHeaders().get("Location"));

        Assertions.assertEquals(expectedJson, actualJson);

        String expectedLocationHeader = response.getHeaders().get("Location");
        String actualLocationHeader = testResult.getHeaders("Location")[0].getValue();
        Assertions.assertEquals(
                expectedLocationHeader,
                actualLocationHeader
        );


    }
}
package org.localhost.httpmodule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.*;
import org.localhost.httpmodule.httpUtils.HttpUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


class HttpHandlerImplTest {

//    test config
    private final HttpHandlerImpl objectUnderTest = new HttpHandlerImpl();
    private final String testUrl = "http://localhost:8089";
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

    @BeforeEach
    void initialize() {
//        http://localhost:54825
        String baseUrl = String.format(
                "http://localhost:%s",
                mockBackEnd.getPort()
        );
    }
//    end of test config



    @Test
    @DisplayName("It should make a succesful get request")
    void getRequestTest() throws JsonProcessingException {
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

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);

        Assertions.assertEquals(expectedJson, actualJson);

    }

    @Test
    @DisplayName("It should perform successful delete request")
    void deleteRequestTest() throws JsonProcessingException {
        String expectedBody = "{\n" +
                "   \"message\": \"Object with id = 6, has been deleted.\"\n" +
                "}";
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

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJson = objectMapper.readTree(expectedBody);
        JsonNode actualJson = objectMapper.readTree(actualBody);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("it should perform successful post request")
    void postRequestTest() throws IOException {
        String expectedBody = "{\n" +
        "   \"id\": \"7\",\n" +
        "   \"name\": \"Apple MacBook Pro 16\",\n" +
        "   \"data\": {\n" +
        "      \"year\": 2019,\n" +
        "      \"price\": 1849.99,\n" +
        "      \"CPU model\": \"Intel Core i9\",\n" +
        "      \"Hard disk size\": \"1 TB\"\n" +
        "   },\n" +
        "   \"createdAt\": \"2022-11-21T20:06:23.986Z\"\n" +
        "}";

        String postReqBody = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2019,\n" +
                "      \"price\": 1849.99,\n" +
                "      \"CPU model\": \"Intel Core i9\",\n" +
                "      \"Hard disk size\": \"1 TB\"\n" +
                "   }\n" +
                "}";
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
    void putRequestTest() {
    }

    @Test
    void patchRequestTest() {
    }
}
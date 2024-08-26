package org.localhost.httpmodule.handler.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.localhost.httpmodule.facade.RequestBuilder.RequestBuilder.RequestBuilderImpl;
import org.localhost.httpmodule.facade.exceptions.NotValidBodyParameterException;
import org.localhost.httpmodule.facade.exceptions.NotValidHeadersParameterException;
import org.localhost.httpmodule.facade.exceptions.NotValidMethodException;
import org.localhost.httpmodule.facade.exceptions.NotValidUrlParameterException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RequestBuilderImplTest {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";
    private final String TEST_STRING_URL = "http://localhost";
    private final ObjectMapper objectMapper = new ObjectMapper();


    private RequestBuilderImpl objectUnderTest;


    @BeforeEach
    void setUp() {
        objectUnderTest = new RequestBuilderImpl();
    }

    @Test
    @DisplayName("createRequestBuilder should create a valid request")
    void createRequestBuilderShouldMatchOkHttpRequest() {
//        given
        Request expectedResult = new Request.Builder().url(TEST_STRING_URL).build();
//        when
        Request testResult = objectUnderTest.createRequestBuilder(TEST_STRING_URL).build();
//        then
        assertEquals(expectedResult.url(), testResult.url());

    }

    @Test
    @DisplayName("createRequestBuilder should throw when given null as input")
    void createRequestBuilder_nullUrl_shouldThrowException() {
        assertThrows(
                NotValidUrlParameterException.class,
                () -> objectUnderTest.createRequestBuilder(null));
    }

    @Test
    @DisplayName("createRequestBuilder should throw when given empty string")
    void createRequestBuilder_emptyString_shouldThrowException() {
        assertThrows(
                NotValidUrlParameterException.class,
                () -> objectUnderTest.createRequestBuilder(""));
    }

    @Test
    @DisplayName("createRequestBuilder should throw when given not valid url")
    void createRequestBuilder_notValidUrl_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> objectUnderTest.createRequestBuilder("afsd")
        );
    }


    @Test
    @DisplayName("addHeaders should add headers to request")
    void addHeadersShouldAddHeadersToRequest() {
//        given
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("header1", "value1");
        Request request = objectUnderTest
                .createRequestBuilder(TEST_STRING_URL)
                .addHeaders(expectedHeaders)
                .build();
//        when, then
        assertEquals(expectedHeaders.get("header1"), request.header("header1"));
    }

    @Test
    @DisplayName("addHeaders should throw when input is null")
    void addHeadersShouldThrowExceptionWhenInputIsNull() {
        assertThrows(
                NotValidHeadersParameterException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL).addHeaders(null)
        );
    }

    @Test
    @DisplayName("addHeaders should throw when input is empty")
    void addHeadersShouldThrowExceptionWhenInputIsEmpty() {
        HashMap<String, String> emptyMap = new HashMap<>();
//        emptyMap.put("", "");
        assertThrows(
                NotValidHeadersParameterException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL).addHeaders(emptyMap)
        );
    }




    @ParameterizedTest
    @ValueSource(strings = {POST, PUT, PATCH})
    @DisplayName("addJsonRequestBody should create request with a valid body for different HTTP methods")
    void addJsonRequestBody(String httpMethod) throws IOException {
        // given
        String body = "{\"username\": \"test\"}";
        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        Request testRequest = objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                .addJsonRequestBody(body, httpMethod)
                .build();

        // then
        assertNotNull(testRequest.body());
        assertEquals(httpMethod, testRequest.method());
        assertEquals("application/json; charset=utf-8",
                testRequest.body().contentType().toString()
        );

        String actualBody = bodyToString(testRequest.body());
        assertEquals(jsonBody, actualBody);
    }

    @ParameterizedTest
    @ValueSource(strings = {GET, DELETE})
    @DisplayName("addJsonRequestBody should throw when methods not suitable for add body")
    void addJsonRequestBodyShouldThrowExceptionWhenMethodsNotSuitableForAddBody(String httpMethod) throws IOException {
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest
                        .createRequestBuilder(TEST_STRING_URL)
                        .addJsonRequestBody(TEST_STRING_URL, httpMethod)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {POST, PUT, PATCH})
    @DisplayName("addJsonRequestBody should throw when body is null")
    void addJsonRequestBodyShouldThrowExceptionWhenBodyIsNull(String httpMethod) throws IOException {
        assertThrows(
                NotValidBodyParameterException.class,
                () -> objectUnderTest
                        .createRequestBuilder(TEST_STRING_URL)
                        .addJsonRequestBody(null, httpMethod)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {POST, PUT, PATCH})
    @DisplayName("addJsonRequestBody should throw when body is empty")
    void addJsonRequestBodyShouldThrowExceptionWhenBodyIsEmpty(String httpMethod) throws IOException {
        assertThrows(
                NotValidBodyParameterException.class,
                () -> objectUnderTest
                        .createRequestBuilder(TEST_STRING_URL)
                        .addJsonRequestBody("", httpMethod)
        );
    }

    @Test
    @DisplayName("AddJsonRequestBody should throw when method is null")
    void addJsonRequestBodyShouldThrowExceptionWhenMethodIsNull() throws IOException {
//      when, then
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                        .addJsonRequestBody("body", null)
        );
    }


    @Test
    @DisplayName("AddJsonRequestBody should throw when method is empty")
    void addJsonRequestBodyShouldThrowExceptionWhenMethodIsEmpty() throws IOException {
//      when, then
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                        .addJsonRequestBody("body", "")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {GET, DELETE})
    @DisplayName("createRequestWithoutBody should create a valid request")
    void createRequestBuilderWithoutBodyShouldCreateRequestWithoutBody(String httpMethod) throws IOException {
//        given
        Request expectedResult;
//        when
        if (httpMethod.equals(GET)) {
            expectedResult = new Request.Builder().url(TEST_STRING_URL).get().build();
        } else {
            expectedResult = new Request.Builder().url(TEST_STRING_URL).delete().build();
        }
        Request testResult = objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                .createRequestWithoutBody(httpMethod).build();
//        then
        assertEquals(expectedResult.url(), testResult.url());
        assertEquals(expectedResult.method(), testResult.method());
    }


    @ParameterizedTest
    @ValueSource(strings = {POST, PUT, PATCH})
    @DisplayName("createRequestWithoutBody should throw when not adequate method used")
    void createRequestBuilderWithoutBodyShouldThrow(String httpMethod) {
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                        .createRequestWithoutBody(httpMethod)
        );
    }

    @Test
    @DisplayName("createRequestWithoutBody should throw when method is null")
    void createRequestBuilderWithoutBodyShouldThrowExceptionWhenMethodIsNull() throws IOException {
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                        .createRequestWithoutBody(null)
        );
    }

    @Test
    @DisplayName("createRequestWithoutBody should throw when method is empty")
    void createRequestBuilderWithoutBodyShouldThrowExceptionWhenMethodIsEmpty() throws IOException {
        assertThrows(
                NotValidMethodException.class,
                () -> objectUnderTest.createRequestBuilder(TEST_STRING_URL)
                        .createRequestWithoutBody("")
        );
    }


    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
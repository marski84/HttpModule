package org.localhost.httpmodule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HttpModuleApplication {

    public static void main(String[] args) {

//        ConfigurableApplicationContext context = SpringApplication.run(HttpModuleApplication.class, args);
//        HttpHandlerImpl httpService = context.getBean(HttpHandlerImpl.class);
//        httpService.get("https://cat-fact.herokuapp.com/facts/random");


        HttpHandlerImpl httpHandler = new HttpHandlerImpl();
        String jsonString = "{ \"name\": \"Apple MacBook Pro 16\", \"data\": { \"year\": 2019, \"price\": 1849.99, \"CPU model\": \"Intel Core i9\"," +
                " \"Hard disk size\": \"1 TB\" } }";


        JsonNode object= httpHandler.post("https://api.restful-api.dev/objects", jsonString);
        httpHandler.delete("https://api.restful-api.dev/objects/ff80818190966d7f01909dfb82081637");
        JsonNode objects = httpHandler.get("https://api.restful-api.dev/objects");


    }

}

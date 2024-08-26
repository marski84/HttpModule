package org.localhost.httpmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HttpModuleApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(HttpModuleApplication.class, args);




    }

}

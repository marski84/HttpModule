package org.localhost.httpmodule.facade.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.localhost")
public class ProjectConfiguration {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}

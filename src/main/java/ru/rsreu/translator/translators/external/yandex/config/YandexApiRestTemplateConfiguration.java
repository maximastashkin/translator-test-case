package ru.rsreu.translator.translators.external.yandex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class YandexApiRestTemplateConfiguration {
    @Bean
    public RestTemplate yandexRestTemplate(
            YandexRestTemplateResponseErrorHandler errorHandler,
            @Value("${translator.yadnex.response_timeout_ms}") long responseTimeout) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setReadTimeout(Duration.ofMillis(responseTimeout))
                .setConnectTimeout(Duration.ofMillis(responseTimeout))
                .build();
    }
}

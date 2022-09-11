package ru.rsreu.translator.translators.external.yandex.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class YandexApiRestTemplateConfiguration {
    @Bean
    public RestTemplate yandexRestTemplate(YandexRestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setReadTimeout(Duration.ofSeconds(1))
                .setConnectTimeout(Duration.ofSeconds(1))
                .build();
    }
}

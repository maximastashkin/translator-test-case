package ru.rsreu.translator.api.translators.external.yandex.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class YandexApiRestTemplateConfiguration {
    @Bean
    public RestTemplate yandexRestTemplate(YandexRestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder().errorHandler(errorHandler).build();
    }
}

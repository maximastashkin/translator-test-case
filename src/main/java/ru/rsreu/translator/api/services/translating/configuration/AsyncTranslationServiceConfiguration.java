package ru.rsreu.translator.api.services.translating.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncTranslationServiceConfiguration {
    @Bean
    public ExecutorService fixedThreadPoolExecutorService(
            @Value("${translation_service.async.thread_pool_size}") int poolSize) {
        return Executors.newFixedThreadPool(poolSize);
    }
}

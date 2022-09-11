package ru.rsreu.translator.api.services.translation.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rsreu.translator.api.services.translation.AsyncTranslatorHolder;
import ru.rsreu.translator.translators.Translator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class TranslationServicesConfiguration {
    @Bean
    public AsyncTranslatorHolder yandexAsyncTranslatorHolder(
            @Value("${translator.yandex.api_hits_per_second}") int hitPerSecond,
            @Value("${translation_service.async.thread_pool_size}") int threadsCount,
            @Qualifier("yandexTranslator") Translator translator,
            @Qualifier("yandexAsyncTranslatorHolderExecutorService") ExecutorService executorService
    ) {
        return new AsyncTranslatorHolder(
                translator, executorService, hitPerSecond, threadsCount);
    }

    @Bean
    public ExecutorService yandexAsyncTranslatorHolderExecutorService(
            @Value("${translation_service.async.thread_pool_size}") int threadsCount) {
        return Executors.newFixedThreadPool(threadsCount);
    }
}

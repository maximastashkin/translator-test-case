package ru.rsreu.translator.api.translators.external.yandex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.translator.api.translators.external.AbstractExternalTranslator;
import ru.rsreu.translator.api.translators.external.yandex.dto.YandexTranslationRequest;
import ru.rsreu.translator.api.translators.external.yandex.dto.YandexTranslatorResponse;

import javax.annotation.PostConstruct;

@Component
public class YandexTranslator extends AbstractExternalTranslator {
    private final Logger logger = LoggerFactory.getLogger(YandexTranslator.class);
    private final String apiKey;
    private final String apiFolderId;

    private HttpHeaders requestHeaders;

    @Autowired
    public YandexTranslator(
            @Value("${translator.yandex.api_url}") String apiUrl,
            @Value("${translator.yandex.api_version}") String apiVersion,
            @Value("${translator.yandex.api_method}") String apiMethod,
            @Value("${translator.yandex.request_method}") HttpMethod apiRequestMethod,
            @Qualifier("yandexRestTemplate") RestTemplate restTemplate,
            @Value("${translator.yandex.api_key}") String apiKey,
            @Value("${translator.yandex.api_folder_id}") String apiFolderId
    ) {
        super(apiUrl, apiVersion, apiMethod, apiRequestMethod, restTemplate);
        this.apiKey = apiKey;
        this.apiFolderId = apiFolderId;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Api-Key " + apiKey);
    }

    @Override
    public String translate(String sourceLanguageCode, String targetLanguageCode, String text) {
        return performApiRequest(buildRequest(sourceLanguageCode, targetLanguageCode, text))
                .getTranslations().get(0).getText();
    }

    private YandexTranslatorResponse performApiRequest(YandexTranslationRequest request) {
        HttpEntity<YandexTranslationRequest> httpEntity = new HttpEntity<>(request, requestHeaders);
        logger.info(
                String.format("Performing translation with yandex ai translation api " +
                                "| source language:%s | target language: %s | text: %s",
                        request.getSourceLanguageCode(), request.getTargetLanguageCode(), request.getTexts())
        );
        return restTemplate.exchange(fullApiPath, apiRequestMethod, httpEntity, YandexTranslatorResponse.class)
                .getBody();
    }

    private YandexTranslationRequest buildRequest(String sourceLanguageCode, String targetLanguageCode, String text) {
        return YandexTranslationRequest.builder()
                .folderId(apiFolderId)
                .texts(text)
                .sourceLanguageCode(sourceLanguageCode)
                .targetLanguageCode(targetLanguageCode).build();
    }
}

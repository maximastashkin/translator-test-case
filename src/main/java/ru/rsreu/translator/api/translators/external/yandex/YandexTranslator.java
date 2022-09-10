package ru.rsreu.translator.api.translators.external.yandex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.translator.api.translators.external.AbstractExternalTranslator;
import ru.rsreu.translator.api.translators.external.ApiRequestMethod;

@Component
public class YandexTranslator extends AbstractExternalTranslator {
    private final String apiKey;

    @Autowired
    public YandexTranslator(
            @Value("${translator.yandex.api_url}") String apiUrl,
            @Value("${translator.yandex.api_version}") String apiVersion,
            @Value("${translator.yandex.api_method}") String apiMethod,
            @Value("${translator.yandex.request_method}") ApiRequestMethod apiRequestMethod,
            RestTemplate restTemplate,
            @Value("${translator.yandex.api_key}") String apiKey) {
        super(apiUrl, apiVersion, apiMethod, apiRequestMethod, restTemplate);
        this.apiKey = apiKey;
    }

    @Override
    public String translate(String sourceLanguageCode, String targetLanguageCode, String text) {
        return null;
    }

    @Override
    public String toString() {
        return "YandexTranslator{" +
                "fullApiPath='" + fullApiPath + '\'' +
                ", apiRequestMethod=" + apiRequestMethod +
                '}';
    }
}

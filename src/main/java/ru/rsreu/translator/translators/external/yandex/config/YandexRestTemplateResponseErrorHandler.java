package ru.rsreu.translator.translators.external.yandex.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.rsreu.translator.translators.external.yandex.exception.YandexApiInternalErrorException;
import ru.rsreu.translator.translators.external.yandex.exception.YandexApiUnsupportedLanguageException;

import java.io.IOException;

@Component
public class YandexRestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode() != HttpStatus.OK;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new YandexApiUnsupportedLanguageException();
        } else {
            throw new YandexApiInternalErrorException();
        }
    }
}

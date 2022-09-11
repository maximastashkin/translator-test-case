package ru.rsreu.translator.api.translators.external.yandex.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.rsreu.translator.api.translators.external.yandex.exception.YandexApiInternalErrorException;
import ru.rsreu.translator.api.translators.external.yandex.exception.YandexApiUnsupportedLanguageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                System.out.println(reader.lines().collect(Collectors.joining()));
            }
            throw new YandexApiInternalErrorException();
        }
    }
}

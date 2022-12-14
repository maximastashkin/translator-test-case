package ru.rsreu.translator.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rsreu.translator.api.controllers.dto.ErrorResponseBody;
import ru.rsreu.translator.api.controllers.dto.ErrorType;
import ru.rsreu.translator.translators.external.yandex.exception.YandexApiInternalErrorException;
import ru.rsreu.translator.translators.external.yandex.exception.YandexApiUnsupportedLanguageException;

import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@RestControllerAdvice(basePackageClasses = WordByWordTranslationController.class)
public class WordByWordTranslationControllerAdvice {
    @ExceptionHandler({NoRouteToHostException.class, UnknownHostException.class, SocketTimeoutException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseBody> handleApiConnectionException() {
        return new ResponseEntity<>(
                new ErrorResponseBody(
                        ErrorType.EXTERNAL_TRANSLATION_API_NOT_AVAILABLE,
                        "External translation api not available now"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(YandexApiInternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseBody> handleYandexApiInternalErrorException() {
        return new ResponseEntity<>(
                new ErrorResponseBody(
                        ErrorType.EXTERNAL_TRANSLATION_API_ERROR,
                        "External api threw unhandled error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(YandexApiUnsupportedLanguageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseBody> handleYandexApiUnsupportedLanguageException() {
        return new ResponseEntity<>(
                new ErrorResponseBody(ErrorType.EXTERNAL_TRANSLATION_API_UNSUPPORTED_LANGUAGE,
                        "Source or target language isn't supported by external translation api"),
                HttpStatus.BAD_REQUEST
        );
    }
}

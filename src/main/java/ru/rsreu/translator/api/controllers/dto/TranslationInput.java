package ru.rsreu.translator.api.controllers.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TranslationInput {
    private final String sourceLanguageCode;
    private final String targetLanguageCode;
    private final String text;

    public static TranslationInput mapRequestBody(TranslationRequestBody requestBody) {
        return new TranslationInput(
                requestBody.getSourceLanguageCode(),
                requestBody.getTargetLanguageCode(),
                requestBody.getText()
        );
    }
}

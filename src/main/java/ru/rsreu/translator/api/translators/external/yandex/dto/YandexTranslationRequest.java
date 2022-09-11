package ru.rsreu.translator.api.translators.external.yandex.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YandexTranslationRequest {
    private final String folderId;
    private final String texts;
    private final String sourceLanguageCode;
    private final String targetLanguageCode;
}

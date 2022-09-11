package ru.rsreu.translator.api.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
public class TranslationRequestEntity {
    private final String ipAddress;
    private final LocalDateTime requestTime;
    private final String text;
    private final String sourceLanguageCode;
    private final String targetLanguageCode;
    private final String translation;
    private final Collection<TranslatedWord> words;
}

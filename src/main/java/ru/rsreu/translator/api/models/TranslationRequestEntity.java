package ru.rsreu.translator.api.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
public class TranslationRequestEntity {
    private long id;
    private String ipAddress;
    private LocalDateTime requestTime;
    private String text;
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String translation;
    private Collection<TranslatedWord> words;
}

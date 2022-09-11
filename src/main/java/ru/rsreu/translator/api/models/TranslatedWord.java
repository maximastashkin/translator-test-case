package ru.rsreu.translator.api.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TranslatedWord {
    private final TranslationRequestEntity request;
    private final String sourceWords;
    private final String translatedWord;
}

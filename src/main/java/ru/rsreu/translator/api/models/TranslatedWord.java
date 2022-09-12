package ru.rsreu.translator.api.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TranslatedWord {
    private long id;
    private TranslationRequestEntity request;
    private String sourceWord;
    private String translatedWord;
}

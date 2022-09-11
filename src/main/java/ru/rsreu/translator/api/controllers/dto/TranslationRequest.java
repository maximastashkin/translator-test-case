package ru.rsreu.translator.api.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TranslationRequest {
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String text;
}

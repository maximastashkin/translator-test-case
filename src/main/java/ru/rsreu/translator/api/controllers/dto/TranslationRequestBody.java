package ru.rsreu.translator.api.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.translator.validation.iso_639_1_lang.Iso639p1Language;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TranslationRequestBody {
    @Iso639p1Language(message = "sourceLanguageCode must be ISO-639-1 standard")
    private String sourceLanguageCode;
    @Iso639p1Language(message = "targetLanguageCode must be ISO-639-1 standard")
    private String targetLanguageCode;
    @NotBlank(message = "text must be not blank")
    private String text;
}

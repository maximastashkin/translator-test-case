package ru.rsreu.translator.api.services.translation.word_by_word;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class WordByWordTranslationResult {
    private final String translatedString;
    private final Map<String, String> translatedWords;
}

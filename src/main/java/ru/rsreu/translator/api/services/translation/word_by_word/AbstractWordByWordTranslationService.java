package ru.rsreu.translator.api.services.translation.word_by_word;

import ru.rsreu.translator.api.controllers.dto.TranslationInput;
import ru.rsreu.translator.api.services.translation.TranslationService;

public abstract class AbstractWordByWordTranslationService implements TranslationService {
    public abstract WordByWordTranslationResult getWordByWordTranslation(TranslationInput translationInput);
}

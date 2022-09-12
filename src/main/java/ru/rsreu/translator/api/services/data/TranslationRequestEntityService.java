package ru.rsreu.translator.api.services.data;

import ru.rsreu.translator.api.controllers.TranslationRequestInfo;
import ru.rsreu.translator.api.controllers.dto.TranslationInput;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordTranslationResult;

public interface TranslationRequestEntityService {
    void saveRequest(
            TranslationInput translationInput,
            TranslationRequestInfo requestInfo,
            WordByWordTranslationResult result
    );
}

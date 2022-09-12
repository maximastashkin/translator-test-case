package ru.rsreu.translator.api.services.translation;

import ru.rsreu.translator.api.controllers.dto.TranslationInput;

public interface TranslationService {
    String translate(TranslationInput translationInput);
}

package ru.rsreu.translator.api.services.translating;

import ru.rsreu.translator.api.translators.Translator;

public abstract class AbstractTranslationService implements TranslationService {
    protected final Translator translator;

    public AbstractTranslationService(Translator translator) {
        this.translator = translator;
    }
}

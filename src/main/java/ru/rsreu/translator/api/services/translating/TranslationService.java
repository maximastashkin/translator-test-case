package ru.rsreu.translator.api.services.translating;

public interface TranslationService {
    String translate(String sourceLanguageCode, String targetLanguageCode, String text);
}

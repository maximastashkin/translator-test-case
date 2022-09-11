package ru.rsreu.translator.api.services.translation;

public interface TranslationService {
    String translate(String sourceLanguageCode, String targetLanguageCode, String text);
}

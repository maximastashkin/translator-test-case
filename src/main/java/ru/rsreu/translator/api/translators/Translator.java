package ru.rsreu.translator.api.translators;

public interface Translator {
    String translate(String sourceLanguageCode, String targetLanguageCode, String text);
}

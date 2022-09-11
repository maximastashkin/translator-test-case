package ru.rsreu.translator.translators;

public interface Translator {
    String translate(String sourceLanguageCode, String targetLanguageCode, String text);
}

package ru.rsreu.translator.api.dao;

import ru.rsreu.translator.api.models.TranslationRequestEntity;

public interface TranslationRequestEntityDao {
    void save(TranslationRequestEntity entity);
}

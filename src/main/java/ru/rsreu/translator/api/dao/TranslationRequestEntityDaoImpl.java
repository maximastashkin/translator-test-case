package ru.rsreu.translator.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.translator.api.models.TranslatedWord;
import ru.rsreu.translator.api.models.TranslationRequestEntity;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TranslationRequestEntityDaoImpl implements TranslationRequestEntityDao {
    private final SimpleJdbcInsert requestsJdbcInsert;
    private final SimpleJdbcInsert wordsJdbcInsert;

    @Autowired
    public TranslationRequestEntityDaoImpl(
            @Qualifier("requestEntitySimpleJdbcInsert") SimpleJdbcInsert requestEntitySimpleJdbcInsert,
            @Qualifier("translatedWordSimpleJdbcInsert") SimpleJdbcInsert translatedWordSimpleJdbcInsert
    ) {
        this.requestsJdbcInsert = requestEntitySimpleJdbcInsert;
        this.wordsJdbcInsert = translatedWordSimpleJdbcInsert;
    }

    @Override
    @Transactional()
    public void save(TranslationRequestEntity entity) {
        Number requestId = requestsJdbcInsert.executeAndReturnKey(formParamsForRequestEntity(entity));
        entity.getWords().forEach(it ->
                wordsJdbcInsert.execute(formParamsForTranslatedWordEntity(it, requestId)));
    }

    private Map<String, Object> formParamsForRequestEntity(TranslationRequestEntity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("ip", entity.getIpAddress());
        params.put("request_date_time", entity.getRequestTime());
        params.put("input_text", entity.getText());
        params.put("source_language_code", entity.getSourceLanguageCode());
        params.put("target_language_code", entity.getTargetLanguageCode());
        params.put("translated_text", entity.getTranslation());
        return params;
    }

    private Map<String, Object> formParamsForTranslatedWordEntity(TranslatedWord translatedWord, Number requestId) {
        Map<String, Object> params = new HashMap<>();
        params.put("request_id", requestId);
        params.put("source_word", translatedWord.getSourceWord());
        params.put("translated_word", translatedWord.getTranslatedWord());
        return params;
    }
}

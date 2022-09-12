package ru.rsreu.translator.api.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rsreu.translator.api.controllers.TranslationRequestInfo;
import ru.rsreu.translator.api.controllers.dto.TranslationInput;
import ru.rsreu.translator.api.dao.TranslationRequestEntityDao;
import ru.rsreu.translator.api.dao.TranslationRequestEntityDaoImpl;
import ru.rsreu.translator.api.models.TranslatedWord;
import ru.rsreu.translator.api.models.TranslationRequestEntity;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordTranslationResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TranslationRequestEntityServiceImpl implements TranslationRequestEntityService {
    private final TranslationRequestEntityDao dao;

    @Autowired
    public TranslationRequestEntityServiceImpl(TranslationRequestEntityDaoImpl dao) {
        this.dao = dao;
    }

    public void saveRequest(TranslationInput translationInput,
                            TranslationRequestInfo requestInfo,
                            WordByWordTranslationResult result) {
        dao.save(mapRequestInfoAndResult(translationInput, requestInfo, result));
    }

    private TranslationRequestEntity mapRequestInfoAndResult(
            TranslationInput translationInput,
            TranslationRequestInfo requestInfo,
            WordByWordTranslationResult result) {
        return TranslationRequestEntity.builder()
                .ipAddress(requestInfo.getIpAddress())
                .requestTime(requestInfo.getRequestTime())
                .text(translationInput.getText())
                .sourceLanguageCode(translationInput.getSourceLanguageCode())
                .targetLanguageCode(translationInput.getTargetLanguageCode())
                .translation(result.getTranslatedString())
                .words(mapTranslatedWords(result.getTranslatedWords()))
                .build();
    }

    private List<TranslatedWord> mapTranslatedWords(Map<String, String> translatedWords) {
        return translatedWords.entrySet().stream().map(it ->
                        TranslatedWord.builder()
                                .sourceWord(it.getKey())
                                .translatedWord(it.getValue())
                                .build())
                .collect(Collectors.toList());
    }
}

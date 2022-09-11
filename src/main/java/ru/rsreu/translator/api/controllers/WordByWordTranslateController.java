package ru.rsreu.translator.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.translator.api.controllers.dto.TranslationRequestBody;
import ru.rsreu.translator.api.controllers.dto.TranslationResponseBody;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordAsyncTranslationService;
import ru.rsreu.translator.api.services.translation.word_by_word.AbstractWordByWordTranslationService;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordTranslationResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/word-by-word-translator")
@Validated
public class WordByWordTranslateController {
    private final AbstractWordByWordTranslationService translatorService;
    private final TranslationRequestInfo translationRequestInfo;

    @Autowired
    public WordByWordTranslateController(WordByWordAsyncTranslationService translatorService,
                                         TranslationRequestInfo translationRequestInfo) {
        this.translatorService = translatorService;
        this.translationRequestInfo = translationRequestInfo;
    }

    @PostMapping("/translate")
    public ResponseEntity<TranslationResponseBody> translate(
            @RequestBody @Valid TranslationRequestBody requestBody) {
        WordByWordTranslationResult result = translatorService.getWordByWordTranslation(
                requestBody.getSourceLanguageCode(),
                requestBody.getTargetLanguageCode(),
                requestBody.getText());
        return ResponseEntity.ok(
                new TranslationResponseBody(
                        result.getTranslatedString()
                ));
    }
}

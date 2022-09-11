package ru.rsreu.translator.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.translator.api.controllers.dto.TranslationRequest;
import ru.rsreu.translator.api.services.translating.TranslationService;

@RestController
@RequestMapping("/translate")
public class TranslateController {
    TranslationService translatorService;

    @Autowired
    public TranslateController(TranslationService translatorService) {
        this.translatorService = translatorService;
    }

    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody TranslationRequest request) {
        return ResponseEntity.ok((translatorService.translate(
                request.getSourceLanguageCode(),
                request.getTargetLanguageCode(),
                request.getText()
        )));
    }
}

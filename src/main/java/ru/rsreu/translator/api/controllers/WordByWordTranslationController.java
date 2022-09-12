package ru.rsreu.translator.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.translator.api.controllers.dto.ErrorResponseBody;
import ru.rsreu.translator.api.controllers.dto.TranslationInput;
import ru.rsreu.translator.api.controllers.dto.TranslationRequestBody;
import ru.rsreu.translator.api.controllers.dto.TranslationResponseBody;
import ru.rsreu.translator.api.services.data.TranslationRequestEntityService;
import ru.rsreu.translator.api.services.data.TranslationRequestEntityServiceImpl;
import ru.rsreu.translator.api.services.translation.word_by_word.AbstractWordByWordTranslationService;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordAsyncTranslationService;
import ru.rsreu.translator.api.services.translation.word_by_word.WordByWordTranslationResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/word-by-word-translator")
@Validated
public class WordByWordTranslationController {
    private final AbstractWordByWordTranslationService translatorService;
    private final TranslationRequestEntityService requestEntityService;

    private final TranslationRequestInfo translationRequestInfo;

    @Autowired
    public WordByWordTranslationController(WordByWordAsyncTranslationService translatorService,
                                           TranslationRequestInfo translationRequestInfo,
                                           TranslationRequestEntityServiceImpl requestEntityService) {
        this.translatorService = translatorService;
        this.translationRequestInfo = translationRequestInfo;
        this.requestEntityService = requestEntityService;
    }

    @Operation(summary = "${api.operation}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responses.ok}"),
            @ApiResponse(responseCode = "400", description = "${api.responses.bad_request}",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))),
            @ApiResponse(responseCode = "500", description = "${api.responses.internal_error}",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)))
    })
    @PostMapping(value = "/translate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TranslationResponseBody> translate(
            @RequestBody @Valid TranslationRequestBody requestBody) {
        TranslationInput translationInput = TranslationInput.mapRequestBody(requestBody);
        WordByWordTranslationResult result = translatorService.getWordByWordTranslation(translationInput);
        requestEntityService.saveRequest(translationInput, translationRequestInfo, result);
        return ResponseEntity.ok(
                new TranslationResponseBody(
                        result.getTranslatedString()
                ));
    }
}

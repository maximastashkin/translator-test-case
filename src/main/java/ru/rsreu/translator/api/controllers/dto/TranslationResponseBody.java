package ru.rsreu.translator.api.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TranslationResponseBody {
    private final String translation;
}

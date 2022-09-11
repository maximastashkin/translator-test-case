package ru.rsreu.translator.api.controllers.dto;

import lombok.Getter;

@Getter
public enum ErrorType {
    BAD_REQUEST(1),
    EXTERNAL_TRANSLATION_API_NOT_AVAILABLE(1),
    EXTERNAL_TRANSLATION_API_ERROR(2);

    private final int code;

    ErrorType(int code) {
        this.code = code;
    }
}

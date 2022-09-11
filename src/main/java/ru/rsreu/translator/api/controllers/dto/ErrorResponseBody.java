package ru.rsreu.translator.api.controllers.dto;

import lombok.Getter;

@Getter
public class ErrorResponseBody {
    private final ErrorType type;
    private final int code;
    private final String message;

    public ErrorResponseBody(ErrorType type, String message) {
        this.type = type;
        this.code = type.getCode();
        this.message = message;
    }
}

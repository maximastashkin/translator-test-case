package ru.rsreu.translator.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.rsreu.translator.api.controllers.dto.ErrorResponseBody;
import ru.rsreu.translator.api.controllers.dto.ErrorType;

@ControllerAdvice
public class ValidationsExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new ErrorResponseBody(
                        ErrorType.BAD_REQUEST,
                        exception.getBindingResult().getFieldError().getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }
}

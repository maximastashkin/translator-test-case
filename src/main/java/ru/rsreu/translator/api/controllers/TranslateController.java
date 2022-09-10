package ru.rsreu.translator.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translate")
public class TranslateController {
    @PostMapping("/translate")
    public ResponseEntity<?> translate() {
        return ResponseEntity.ok().build();
    }
}

package ru.rsreu.translator.api.services.string_utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class StringIsWordChecker {
    private final Pattern pattern = Pattern.compile("\\w+");

    public boolean isWord(String word) {
        return pattern.matcher(word).matches();
    }
}

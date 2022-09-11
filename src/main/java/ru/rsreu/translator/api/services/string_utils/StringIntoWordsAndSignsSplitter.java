package ru.rsreu.translator.api.services.string_utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StringIntoWordsAndSignsSplitter {
    public List<String> splitIntoWords(String splittableString) {
        return Arrays.asList(splittableString.split("\\b"));
    }
}

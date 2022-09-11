package ru.rsreu.translator.validation.iso_639_1_lang;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Iso639p1LanguageValidator implements ConstraintValidator<Iso639p1Language, String> {
    private static final Set<String> ISO_LANGUAGES = new HashSet<>(Arrays.asList(Locale.getISOLanguages()));

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ISO_LANGUAGES.contains(s);
    }
}

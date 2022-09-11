package ru.rsreu.translator.validation.iso_639_1_lang;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = Iso639p1LanguageValidator.class)
public @interface Iso639p1Language {
    String message() default "language must be ISO-639-1 standard";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

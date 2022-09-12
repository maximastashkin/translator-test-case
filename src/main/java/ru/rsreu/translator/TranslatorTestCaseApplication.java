package ru.rsreu.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(@PropertySource("classpath:swagger.properties"))
public class TranslatorTestCaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(TranslatorTestCaseApplication.class, args);
    }
}

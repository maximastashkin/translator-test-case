package ru.rsreu.translator.api.dao.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean
    public SimpleJdbcInsert requestEntitySimpleJdbcInsert(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource).withTableName("translation_requests")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert translatedWordSimpleJdbcInsert(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource).withTableName("translated_words")
                .usingGeneratedKeyColumns("id");
    }
}

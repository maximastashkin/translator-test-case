CREATE TABLE IF NOT EXISTS translation_requests(
    id IDENTITY PRIMARY KEY,
    ip VARCHAR(32) not null,
    request_date_time TIMESTAMP not null,
    input_text VARCHAR not null ,
    source_language_code VARCHAR(2) not null,
    target_language_code VARCHAR(2) not null,
    translated_text VARCHAR not null
);

CREATE TABLE IF NOT EXISTS translated_words(
    id IDENTITY PRIMARY KEY,
    request_id BIGINT not null,
    source_word VARCHAR(30) not null,
    translated_word VARCHAR(30) not null,
    FOREIGN KEY (request_id) references translation_requests(id)
);
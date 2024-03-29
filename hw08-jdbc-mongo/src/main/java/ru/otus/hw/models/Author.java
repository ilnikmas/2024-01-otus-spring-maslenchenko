package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
@AllArgsConstructor
@Getter
public class Author {
    @Transient
    public static final String SEQUENCE_NAME = "authors_sequence";

    @Id
    private Long id;

    private String fullName;
}
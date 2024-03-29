package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "genres")
@AllArgsConstructor
@Getter
public class Genre {
    @Transient
    public static final String SEQUENCE_NAME = "genres_sequence";

    @Id
    private long id;

    private String name;
}
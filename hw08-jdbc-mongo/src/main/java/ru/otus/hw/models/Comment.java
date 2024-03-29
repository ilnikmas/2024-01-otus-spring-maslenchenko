package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@AllArgsConstructor
@Getter
public class Comment {
    @Id
    private long id;

    private long bookId;

    private String text;
}
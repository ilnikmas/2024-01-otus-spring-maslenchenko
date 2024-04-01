package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий на основе MongoRepository для работы с комментариями")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.save(new Comment(1L, 1L, "Comment_1"));
        mongoTemplate.save(new Comment(2L, 2L, "Comment_2"));
        mongoTemplate.save(new Comment(3L, 3L, "Comment_3"));
        mongoTemplate.save(new Comment(4L, 1L, "Comment_4"));
        mongoTemplate.save(new Comment(5L, 2L, "Comment_5"));
        mongoTemplate.save(new Comment(6L, 3L, "Comment_6"));
    }

    @DisplayName("должен возвращать комментарий по id комментария")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void findById(long commentId) {
        var actualComment = commentRepository.findById(commentId);
        var expectedComment = mongoTemplate.findById(commentId, Comment.class);
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен возвращать список всех комментариев по id книги")
    @Test
    void findAllByBookId() {
        var expectedComments = new ArrayList<Comment>();
        expectedComments.add(mongoTemplate.findById(1, Comment.class));
        expectedComments.add(mongoTemplate.findById(4, Comment.class));
        var actualComments = commentRepository.findAllByBookId(1);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }
}
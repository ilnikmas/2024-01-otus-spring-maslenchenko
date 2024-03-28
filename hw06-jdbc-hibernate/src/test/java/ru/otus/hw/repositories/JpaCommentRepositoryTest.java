package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
@Import(JpaCommentRepository.class)
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository commentRepository;

    @DisplayName("должен возвращать комментарий по id комментария")
    @ParameterizedTest
    @MethodSource("getDbComments")
    void findById(Comment expectedComment) {
        var actualComment = commentRepository.findById(expectedComment.getId());
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен возвращать список всех комментариев по id книги")
    @Test
    void findAllByBookId() {
        var expectedComments = getDbComments().subList(0, 2);
        var actualComment = commentRepository.findAllByBookId(1);

        assertThat(actualComment).containsExactlyElementsOf(expectedComments);
        actualComment.forEach(System.out::println);
    }

    private static List<Comment> getDbComments() {
        var dbComments = new ArrayList<Comment>();
        AtomicInteger commentId = new AtomicInteger(0);
        for (int i = 1; i < 4; i++) {
            dbComments.add(new Comment(commentId.incrementAndGet(), i, "Comment_" + commentId));
            dbComments.add(new Comment(commentId.incrementAndGet(), i, "Comment_" + commentId));
        }
        return dbComments;
    }
}
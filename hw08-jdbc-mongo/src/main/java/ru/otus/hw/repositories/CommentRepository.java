package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    Comment insert(Comment comment);
}
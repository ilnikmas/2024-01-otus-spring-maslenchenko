package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public JpaCommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "select c from Comment c " +
                        "where c.bookId=:id",
                Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}

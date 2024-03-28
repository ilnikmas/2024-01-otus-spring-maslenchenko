package ru.otus.hw.repositories;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.*;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public JpaBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("books-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery(
                "select b from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where b.id=:id",
                Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("id", id);
        Book book;
        try {
            book = query.getSingleResult();
            return Optional.of(book);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("books-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery(
                "select b from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Book where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
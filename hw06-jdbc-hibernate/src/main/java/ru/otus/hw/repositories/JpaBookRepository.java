package ru.otus.hw.repositories;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

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
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Book where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    public Book update(Book book) {
        Query query = entityManager.createQuery("update Book b set b.title=:title, b.author=:author, b.genre=:genre " +
                "where b.id=:id");
        query.setParameter("id", book.getId());
        query.setParameter("title", book.getTitle());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
        int rowsUpdated = query.executeUpdate();
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        if (rowsUpdated == 0) {
            throw new EntityNotFoundException("No records was updated");
        }

        return book;
    }
}

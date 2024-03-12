package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcBookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book;
        try {
            book = jdbc.queryForObject("select b.id as book_id, b.title as book_title, " +
                    "a.id as author_id, a.full_name as author_name, " +
                    "g.id as genre_id, g.name as genre_name " +
                    "from authors a " +
                    "inner join books b on a.id=b.author_id " +
                    "inner join genres g on b.genre_id=g.id " +
                    "where b.id = :id", params, new BookRowMapper());
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return book == null ? Optional.empty() : Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select b.id as book_id, b.title as book_title, " +
                "a.id as author_id, a.full_name as author_name, " +
                "g.id as genre_id, g.name as genre_name " +
                "from authors a " +
                "inner join books b on a.id=b.author_id " +
                "inner join genres g on b.genre_id=g.id ", new BookRowMapper());
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
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }

    @SuppressWarnings("DataFlowIssue")
    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        var params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor().getId())
                .addValue("genre", book.getGenre().getId());
        jdbc.update("insert into books (title, author_id, genre_id) values (:title, :author, :genre)",
                params, keyHolder);
        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        var params = new MapSqlParameterSource()
                .addValue("book_id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor().getId())
                .addValue("genre", book.getGenre().getId());
        int updatedRecordsCount = jdbc.update("update books set title=:title, author_id=:author, genre_id=:genre " +
                "where id=:book_id", params);
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        if (updatedRecordsCount == 0) {
            throw new EntityNotFoundException("No records was updated");
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            String bookTitle = rs.getString("book_title");
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            return new Book(bookId, bookTitle, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}

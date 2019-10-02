package com.example.bookservice.dao;

import com.example.bookservice.dto.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDaoJdbcTemplateImpl implements BookDao {

    /*
    book_id int not null auto_increment primary key,
    title varchar(50) not null,
    author varchar(50) not null
     */

    // PREPARED STATEMENTS
    public static final String INSERT_BOOK=
            "insert into book(title, author) values (?,?)";

    public static final String SELECT_BOOK=
            "select * from book where book_id = ?";

    public static final String SELECT_ALL_BOOKS=
            "select * from book";

    public static final String UPDATE_BOOK=
            "update book set title = ?, author = ? where book_id = ?";

    public static final String DELETE_BOOK=
            "delete from book where book_id = ?";

    private JdbcTemplate jdbcTemplate;

    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book createBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK,
                book.getTitle(),
                book.getAuthor());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        book.setBookId(id);
        return book;
    }

    @Override
    public Book getbook(int bookId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BOOK, this::mapRowToBook, bookId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS, this::mapRowToBook);
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(UPDATE_BOOK,
                book.getTitle(),
                book.getAuthor(),
                book.getBookId());
    }

    @Override
    public void deleteBook(int bookId) {
        jdbcTemplate.update(DELETE_BOOK, bookId);
    }

    // ROW MAPPER

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        return book;


    }
}

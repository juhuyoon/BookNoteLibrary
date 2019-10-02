package com.example.bookservice.dao;

import com.example.bookservice.dto.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    private Book book1, book2;

    @Before
    public void setUp() throws Exception {
        bookDao.getAllBooks().forEach(b ->
        {
            bookDao.deleteBook(b.getBookId());
        });

        book1 = new Book();
        book1.setTitle("Redwall");
        book1.setAuthor("Brian Jacques");

        book2 = new Book();
        book2.setTitle("Stranger In a Strange Land");
        book2.setAuthor("Robert A. Heinlein");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createGetDeleteBook() {
        book1 = bookDao.createBook(book1);
        assertEquals(1, bookDao.getAllBooks().size());
        Book fromDao = bookDao.getbook(book1.getBookId());
        assertEquals(book1, fromDao);
        bookDao.deleteBook(book1.getBookId());
        fromDao = bookDao.getbook(book1.getBookId());
        assertNull(fromDao);
    }

    @Test
    public void getAllBooks() {
        bookDao.createBook(book1);
        bookDao.createBook(book2);
        List<Book> bList = bookDao.getAllBooks();
        assertEquals(2, bList.size());

    }

    @Test
    public void updateBook() {
        bookDao.createBook(book1);
        book1.setAuthor("SOMEONE ELSE");
        Book fromDao = bookDao.getbook(book1.getBookId());

        assertNotEquals(book1, fromDao);
    }

}
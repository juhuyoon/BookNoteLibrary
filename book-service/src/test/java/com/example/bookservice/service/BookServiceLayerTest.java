package com.example.bookservice.service;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dao.BookDaoJdbcTemplateImpl;
import com.example.bookservice.dto.Book;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.dto.Note;
import com.example.bookservice.util.feign.NoteServerClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceLayerTest {

    private BookServiceLayer service;
    private BookDao bookDao;
    private NoteServerClient client;
    private RabbitTemplate rabbitTemplate;


    @Before
    public void setUp() throws Exception {
        setUpBookDaoMock();
        setUpBVMMock();

        service = new BookServiceLayer(bookDao, client, rabbitTemplate);

    }


    @Test
    public void createGetBook() {

        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Redwall");
        book.setAuthor("Brian Jacques");

        bookDao.createBook(book);

        Note note = new Note();
        note.setNoteId(1);
        note.setBookId(1);
        note.setNote("This is a note");

        Note note2 = new Note();
        note2.setNoteId(2);
        note2.setBookId(1);
        note2.setNote("This is a second note");

        List<Note> nList = new ArrayList<>();
        nList.add(note);
        nList.add(note2);

        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNotes(nList);


        BookViewModel fromService = service.getBook(book.getBookId());

        assertEquals(bvm, fromService);
    }

    @Test
    public void getAllBooks() {
    }

    @Test
    public void deleteBook() {
    }

    @Test
    public void updateBook() {
    }

    //Helper Methods
    private void setUpBookDaoMock() {
        bookDao = mock(BookDaoJdbcTemplateImpl.class);
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Redwall");
        book.setAuthor("Brian Jacques");

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setTitle("Stranger In a Strange Land");
        book2.setAuthor("Robert A. Heinlein");

        List<Book> bList = new ArrayList<>();
        bList.add(book);

        doReturn(book).when(bookDao).createBook(book2);
        doReturn(book).when(bookDao).getBook(1);
        doReturn(bList).when(bookDao).getAllBooks();
    }

    private void setUpBVMMock() {
        bookDao = mock(BookDaoJdbcTemplateImpl.class);

        Note note = new Note();
        note.setNoteId(1);
        note.setBookId(1);
        note.setNote("This is a note");

        Note note2 = new Note();
        note2.setNoteId(2);
        note2.setBookId(1);
        note2.setNote("This is a second note");

        Note note3 = new Note();
        note3.setNoteId(3);
        note3.setBookId(2);
        note3.setNote("This is a third note");

        List<Note> nList = new ArrayList<>();
        nList.add(note);
        nList.add(note2);

        List<Note> nList2 = new ArrayList<>();
        nList2.add(note3);
        
        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(1);
        bvm.setTitle("Redwall");
        bvm.setAuthor("Brian Jacques");
        bvm.setNotes(nList);

        BookViewModel bvm2 = new BookViewModel();
        bvm2.setBookId(2);
        bvm2.setTitle("Stranger In a Strange Land");
        bvm2.setAuthor("Robert A. Heinlein");


    }

    @After
    public void tearDown() throws Exception {

    }
}
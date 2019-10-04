package com.example.bookservice.service;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dto.Book;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.util.messages.Note;
import com.example.bookservice.util.feign.NoteServerClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceLayer {

    BookDao bookDao;

    NoteServerClient client;

    BookServiceLayer service;

    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.service.controller";

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public BookServiceLayer(BookDao bookDao, NoteServerClient client, RabbitTemplate rabbitTemplate) {
        this.bookDao = bookDao;
        this.client = client;
        this.rabbitTemplate = rabbitTemplate;
    }

    public BookViewModel getBook(int id){
        Book book = bookDao.getBook(id);
        BookViewModel bvm = new BookViewModel();

        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNotes(client.getNotesByBook(id));

        return bvm;
    }

    public List<BookViewModel> getAllBooks() {
        List<Book> bList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();
        //grab the notes
        for(Book book: bList) {
            BookViewModel bvm = buildBookViewModel(book);
                bvmList.add(bvm);
        }

        return bvmList;
    }

    public BookViewModel addBook(BookViewModel bvm) {
            Book book = new Book();
            book.setTitle(bvm.getTitle());
            book.setAuthor(bvm.getAuthor());
            book = bookDao.createBook(book);

            bvm.setBookId(book.getBookId());

            List<Note> nList = bvm.getNotes();

            for(Note note : nList) {
                if (note.getNoteId() == null) {
                    note.setNoteId(0);
                }
                System.out.println("SENDING MESSAGE");
                rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
                System.out.println("TAKE MY MESSAGE :^)");
            }
        return bvm;
    }

    public void updateBook(BookViewModel bvm){
        Book book = new Book();
        book.setBookId(bvm.getBookId());
        book.setTitle(bvm.getTitle());
        book.setAuthor(bvm.getAuthor());
        bookDao.updateBook(book);

        List<Note> nList = bvm.getNotes();

        for(Note note : nList) {
            if (note.getNoteId() == null) {
                note.setNoteId(0);
            }
            System.out.println("SENDING MESSAGE");
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
            System.out.println("TAKE MY MESSAGE :^)");
        }
    }

    public void deleteBook(int id) {
        if(bookDao.getBook(id) == null) {
            System.out.println("Book did not exist");
        }
        List<Note> nList = client.getNotesByBook(id);

        nList.stream()
                .forEach(note -> client.deleteNotes(note.getBookId()));

        bookDao.deleteBook(id);
    }

    private BookViewModel buildBookViewModel(Book book) {
        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNotes(client.getNotesByBook(book.getBookId()));

        return bvm;
    }

}

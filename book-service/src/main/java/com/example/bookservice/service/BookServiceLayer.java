package com.example.bookservice.service;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dto.Book;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.dto.Note;
import com.example.bookservice.util.feign.NoteServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceLayer {

    BookDao bookDao;

    @Autowired
    NoteServerClient client;

    public BookServiceLayer(BookDao bookDao, NoteServerClient client) {
        this.bookDao = bookDao;
        this.client = client;
    }

    public BookViewModel getBook(int id){
        Book book = bookDao.getbook(id);
        BookViewModel bvm = new BookViewModel();

        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNoteList(client.getNotesByBook(id));

        return bvm;
    }

    public List<BookViewModel> getAllBooks() {
        List<Book> bList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();
        for(Book book : bList) {
            BookViewModel bvm = buildBookViewModel(book);
                bvmList.add(bvm);
        }
        return bvmList;
    }

    public BookViewModel addBook(BookViewModel bvm) {
        Book book = new Book();
        book.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());

        book = bookDao.createBook(book);

        Note note = new Note();
        note.setBookId(book.getBookId());
        note.setNote(note.getNote());

        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNote(note);
        client.postNotes(bvm.getNote());

        return bvm;
    }

    public void deleteBook(int id) {
        bookDao.deleteBook(id);
        client.deleteNotes(id);
    }

    public void updateBook(BookViewModel bvm) {
        Book book = new Book();
        book.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());
        bookDao.updateBook(book);

        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNote(client.updateNotes(book.getBookId()));
    }

    private BookViewModel buildBookViewModel(Book book) {
        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
        bvm.setNoteList(client.getNotesByBook(book.getBookId()));

        return bvm;
    }

}

package com.example.bookservice.service;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dto.Book;
import com.example.bookservice.dto.BookViewModel;
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
//        bvm.setNote(client.getNotesByBook());

        return bvm;
    }

    public List<BookViewModel> getAllBooks() {
        BookViewModel bvm = new BookViewModel();
        List<BookViewModel> bvmList = new ArrayList<>();

        bvmList.add(bvm);

        return bvmList;
    }

    public BookViewModel addBook(BookViewModel bvm) {
        Book book = new Book();
        book.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());

        book = bookDao.createBook(book);


        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());
//        bvm.setNote(client.);
//        client.postNotes(bvm.getBookId(), bvm.getNote());

        return bvm;
    }

    public void deleteBook(int id) {
        bookDao.deleteBook(id);

    }

    public void updateBook(BookViewModel bvm) {

    }

}

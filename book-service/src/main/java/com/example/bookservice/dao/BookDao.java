package com.example.bookservice.dao;

import com.example.bookservice.dto.Book;

import java.util.List;

public interface BookDao {

    Book createBook (Book book);

    Book getBook(int bookId);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBook(int bookId);
}

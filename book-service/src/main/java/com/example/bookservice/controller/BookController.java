package com.example.bookservice.controller;

import com.example.bookservice.dto.Book;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.dto.Note;
import com.example.bookservice.service.BookServiceLayer;
import com.example.bookservice.util.feign.NoteServerClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookServiceLayer service;

    // =========== ADD BOOK ===========

//    @PostMapping
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody BookViewModel bvm) {
       service.addBook(bvm);
       // queue
        return null;
    }

    // =========== GET ALL BOOKS ===========

//    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<BookViewModel> getAllBooks() {
        return service.getAllBooks();
    }

    // =========== GET BOOK ===========

//    @GetMapping
    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public BookViewModel getBook(@PathVariable int bookId) {
        return service.getBook(bookId);
    }

    // =========== UPDATE BOOK ===========

//    @PutMapping(value = "/{bookId}")
    @RequestMapping(value = "/{bookId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateBook(@RequestBody BookViewModel bvm, @PathVariable int bookId) {
        bvm.setBookId(bookId);
        service.updateBook(bvm);
    }

    // =========== DELETE BOOK ===========

//    @DeleteMapping(value = "/{bookId}")
    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteBook(@PathVariable int bookId) {
        service.deleteBook(bookId);
    }

}

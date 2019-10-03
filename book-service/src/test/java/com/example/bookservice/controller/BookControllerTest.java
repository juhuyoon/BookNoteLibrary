package com.example.bookservice.controller;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.service.BookServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceLayer bookService;

    @MockBean
    private BookDao bookDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    // =====================================================

    @Test
    public void getBook() throws Exception {
        /*
        private int bookId;
        private String title;
        private String author;
        private List<Note> note;
         */
        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(1);
        bvm.setTitle("Book Title");
        bvm.setAuthor("Book Author");
        bvm.setNote();
    }

    @Test
    public void createBook() {
    }

    @Test
    public void getAllBooks() {
    }

    @Test
    public void updateBook() {
    }

    @Test
    public void deleteBook() {
    }
}
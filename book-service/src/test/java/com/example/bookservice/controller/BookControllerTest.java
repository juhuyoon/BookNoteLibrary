package com.example.bookservice.controller;

import com.example.bookservice.dao.BookDao;
import com.example.bookservice.dto.BookViewModel;
import com.example.bookservice.service.BookServiceLayer;
import com.example.bookservice.util.messages.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void getBookReturnWithJson() throws Exception {

        Note note = new Note();
        note.setBookId(1);
        note.setNoteId(1);
        note.setNote("FIRST NOTE");

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNoteId(2);
        note2.setNote("SECOND NOTE");

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note2);

        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(1);
        bvm.setTitle("Book Title");
        bvm.setAuthor("Book Author");
        bvm.setNotes(noteList);

        String outputJson = mapper.writeValueAsString(bvm);

        when(bookService.getBook(1)).thenReturn(bvm);

        this.mockMvc.perform(get("/books/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getBookReturnWith404() throws Exception {

        when(bookService.getBook(100)).thenThrow(new IllegalArgumentException("Could not find book with matching id"));

        this.mockMvc.perform(get("books/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createBook() throws Exception {
        Note note = new Note();
        note.setBookId(1);
        note.setNoteId(1);
        note.setNote("FIRST NOTE");

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNoteId(2);
        note2.setNote("SECOND NOTE");

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note2);

        BookViewModel bvm = new BookViewModel();
        bvm.setTitle("Book Title");
        bvm.setAuthor("Book Author");
        bvm.setNotes(noteList);

        String inputJson = mapper.writeValueAsString(bvm);

        BookViewModel bvmOut = new BookViewModel();
        bvmOut.setBookId(1);
        bvmOut.setTitle("Book Title");
        bvmOut.setAuthor("Book Author");
        bvmOut.setNotes(noteList);

        String outputJson = mapper.writeValueAsString(bvmOut);

        when(bookService.addBook(bvm)).thenReturn(bvmOut);

        this.mockMvc.perform(post("/books")
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getAllBooks() throws Exception {
        Note note = new Note();
        note.setBookId(1);
        note.setNoteId(1);
        note.setNote("FIRST NOTE");

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNoteId(2);
        note2.setNote("SECOND NOTE");

        Note note3 = new Note();
        note3.setBookId(2);
        note3.setNoteId(1);
        note3.setNote("FIRST NOTE OF SECOND BOOK");

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note2);

        List<Note> noteList2 = new ArrayList<>();
        noteList2.add(note3);

        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(1);
        bvm.setTitle("Book Title");
        bvm.setAuthor("Book Author");
        bvm.setNotes(noteList);

        BookViewModel bvm2 = new BookViewModel();
        bvm2.setBookId(2);
        bvm2.setTitle("Book Title2");
        bvm2.setAuthor("Book Author2");
        bvm2.setNotes(noteList2);

        List<BookViewModel> bvmList = new ArrayList<>();
        bvmList.add(bvm);
        bvmList.add(bvm2);

        when(bookService.getAllBooks()).thenReturn(bvmList);

        List<BookViewModel> listChecker = new ArrayList<>();
        listChecker.addAll(bvmList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateBook() throws Exception {
        Note note = new Note();
        note.setBookId(1);
        note.setNoteId(1);
        note.setNote("FIRST NOTE");

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNoteId(2);
        note2.setNote("SECOND NOTE");

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note2);

        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(1);
        bvm.setTitle("Book Title");
        bvm.setAuthor("Book Author");
        bvm.setNotes(noteList);

        // Keep the same
        String inputJson = mapper.writeValueAsString(bvm);
        String outputJson = mapper.writeValueAsString(bvm);

        this.mockMvc.perform(put("/books/1")
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/books/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
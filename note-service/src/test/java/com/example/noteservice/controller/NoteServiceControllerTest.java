package com.example.noteservice.controller;

import com.example.noteservice.dao.NoteDao;
import com.example.noteservice.dto.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteServiceController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class NoteServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Qualifier("noteDao")
    @MockBean
    private NoteDao noteDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addNoteShouldReturnSavedNote() {

        //simulated Note object coming into controller
        Note inputNote = new Note();
        inputNote.setBookId(1);
        inputNote.setNote("TestNote");

        //Object to JSON in String
        String inputJson;
        try {
            inputJson = mapper.writeValueAsString(inputNote);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        //simulated Note object coming out of controller
        Note outputNote = new Note();
        outputNote.setBookId(1);
        outputNote.setNote("TestNote");
        outputNote.setNoteId(1);

        //Object to JSON in String
        String outputJson;
        try {
            outputJson = mapper.writeValueAsString(outputNote);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        //Mocking Dao response
        when(noteDao.addNote(inputNote)).thenReturn(outputNote);

        //testing the controller using mockMvc
        try {
            this.mockMvc.perform(post("/notes")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(outputJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNoteByIdShouldReturnNoteWithId() {

        //since findNoteById does not take in request body, only need to set up object being returned
        Note outputNote = new Note();
        outputNote.setBookId(1);
        outputNote.setNote("TestNote");
        outputNote.setNoteId(1);

        //Object to JSON in String
        String outputJson;
        try {
            outputJson = mapper.writeValueAsString(outputNote);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        // Mocking DAO response
        when(noteDao.getNote(1)).thenReturn(outputNote);

        try {
            this.mockMvc.perform(get("/notes/" + outputNote.getNoteId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    //use the objectmapper output with the json method
                    .andExpect(content().json(outputJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNoteThatDoesNotExistShouldReturn404() {
        int idForNoteThatDoesNotExist = 111;

        when(noteDao.getNote(idForNoteThatDoesNotExist)).thenReturn(null);

        try {
            this.mockMvc.perform(get("/notes/" + idForNoteThatDoesNotExist))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNotesByBookIdShouldReturnListOfNotesWith2() {
        Note outputNote1 = new Note();
        outputNote1.setBookId(1);
        outputNote1.setNote("TestNote1");
        outputNote1.setNoteId(1);

        Note outputNote2 = new Note();
        outputNote2.setBookId(1);
        outputNote2.setNote("TestNote2");
        outputNote2.setNoteId(2);

        List<Note> noteList = new ArrayList<>();
        noteList.add(outputNote1);
        noteList.add(outputNote2);

        //Object to JSON in String
        when(noteDao.getNotesByBookId(1)).thenReturn(noteList);

        List<Note> listChecker = new ArrayList<>();
        listChecker.addAll(noteList);

        String outputJson;
        try {
            outputJson = mapper.writeValueAsString(listChecker);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        try {
            this.mockMvc.perform(get("/notes/book/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(outputJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllNotesShouldReturnListOfNotes() {
        Note outputNote1 = new Note();
        outputNote1.setBookId(1);
        outputNote1.setNote("TestNote1");
        outputNote1.setNoteId(1);

        Note outputNote2 = new Note();
        outputNote2.setBookId(1);
        outputNote2.setNote("TestNote2");
        outputNote2.setNoteId(2);

        List<Note> noteList = new ArrayList<>();
        noteList.add(outputNote1);
        noteList.add(outputNote2);

        //Object to JSON in String
        when(noteDao.getAllNotes()).thenReturn(noteList);

        List<Note> listChecker = new ArrayList<>();
        listChecker.addAll(noteList);

        String outputJson;
        try {
            outputJson = mapper.writeValueAsString(listChecker);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        try {
            this.mockMvc.perform(get("/notes"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(outputJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateNoteIsOk() {

        //simulated Note object coming into controller
        Note inputNote = new Note();
        inputNote.setBookId(1);
        inputNote.setNote("TestNoteUpdate");
        inputNote.setNoteId(1);

        //Object to JSON in String
        String inputJson;
        try {
            inputJson = mapper.writeValueAsString(inputNote);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        //can't mock the call to update. it returns void. check status only.
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.put("/notes/1")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteNoteIsOk() {
        //can't mock the call to delete. it returns void. check status only.
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/notes/1"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
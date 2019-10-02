package com.example.noteservice.dao;

import com.example.noteservice.dto.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoTest {

    @Autowired
    NoteDao noteDao;

    @Before
    public void setUp() throws Exception {

        List<Note> notes = noteDao.getAllNotes();
        for (Note note : notes) {
            noteDao.deleteNote(note.getNoteId());
        }
    }

    @Test
    public void addGetDeleteNote() {
            Note note = new Note();
            note.setBookId(1);
            note.setNote("Aaron rock solid");

            note = noteDao.addNote(note);

            Note note1 = noteDao.getNote(note.getNoteId());
            assertEquals(note1, note);

            noteDao.deleteNote(note.getNoteId());
            note1 = noteDao.getNote(note.getNoteId());
            assertNull(note1);
        }



    @Test
    public void getAllNotes() {
        Note note = new Note();
        note.setBookId(1);
        note.setNote("Aaron rock solid");

        note = noteDao.addNote(note);

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNote("Aaron rock 2 solid");

        note = noteDao.addNote(note2);

            List<Note> noteList = noteDao.getAllNotes();
            assertEquals(2, noteList.size());
        }

    @Test
    public void updateNote() {

            Note note = new Note();
            note.setModel("Nintendo Switch");
            note.setManufacturer("Nintendo");
            note.setMemory_amount("32GB");
            note.setProcessor("NVIDIA Custom Tegra");
            note.setPrice(new BigDecimal("299.99"));
            note.setQuantity(25);

            note = noteDao.addNote(note);

            note.setPrice(new BigDecimal("324.99"));
            noteDao.updateNote(note);

            Note note1 = noteDao.getNote(note.getNoteId());
            assertEquals(note1, note);
        }


    @Test
    public void getNotesByBookId() {
            Note note = new Note();
            note.setModel("Nintendo Switch");
            note.setManufacturer("Nintendo");
            note.setMemory_amount("32GB");
            note.setProcessor("NVIDIA Custom Tegra");
            note.setPrice(new BigDecimal("299.99"));
            note.setQuantity(25);

            note = noteDao.addNote(note);

            Note note1 = new Note();
            note1.setModel("PS4");
            note1.setManufacturer("PlayStation");
            note1.setMemory_amount("8GB");
            note1.setProcessor("Vr Processor");
            note1.setPrice(new BigDecimal("299.99"));
            note1.setQuantity(20);
            noteDao.addNote(note1);

            List<Note> noteList = noteDao.getNotesByManufacturer(note.getManufacturer());
            assertEquals(1, noteList.size());
        }
}
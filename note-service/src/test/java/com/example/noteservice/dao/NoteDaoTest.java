package com.example.noteservice.dao;

import com.example.noteservice.dto.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteDaoTest {

    @Qualifier("noteDao") // ensures that the repository designated as "noteDao" is autowired.
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

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNote("Aaron rock 2 solid");

        note2 = noteDao.addNote(note2);

        note2.setBookId(12);
        note2.setNote("Aaron rock 2000 solid");

        noteDao.updateNote(note2);

            Note note = noteDao.getNote(note2.getNoteId());
            assertEquals(note2, note);
        }


    @Test
    public void getNotesByBookId() {
        Note note1 = new Note();
        note1.setBookId(1);
        note1.setNote("Aaron rock 2 solid");

        note1 = noteDao.addNote(note1);

        Note note2 = new Note();
        note2.setBookId(1);
        note2.setNote("Aaron rock 2 solid");

        note2 = noteDao.addNote(note2);

            List<Note> noteList = noteDao.getNotesByBookId(1);
            assertEquals(2, noteList.size());
        }
}
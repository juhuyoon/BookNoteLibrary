package com.example.noteservice.controller;

import com.example.noteservice.dao.NoteDao;
import com.example.noteservice.dto.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class NoteServiceController {

    @Autowired
    NoteDao noteDao;

    @RequestMapping(value ="/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note addNote(@RequestBody Note note) {
        return noteDao.addNote(note);
    }

    @RequestMapping(value="/notes/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Note getNoteById(@PathVariable(name="id") Integer id) {
        Note foundNote = noteDao.getNote(id);
        //Throw exception if object is null, else return the found object
        if (foundNote == null) {
            throw new IllegalArgumentException("There is no note with id: " + id );
        } else {
            return foundNote;
        }
    }

    @RequestMapping(value="/notes/book/{book_id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> getNotesByBookId(@PathVariable(name="book_id") Integer bookId) {
        return noteDao.getNotesByBookId(bookId);
    }

    @RequestMapping(value="/notes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> getAllNotes() {
        return noteDao.getAllNotes();
    }

    @RequestMapping(value="/notes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateNote(@RequestBody Note note, @PathVariable(name="id") Integer id) {
        note.setNoteId(id);
        noteDao.updateNote(note);
    }

    @RequestMapping(value="/notes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteNote(@PathVariable(name = "id") Integer id) {
        noteDao.deleteNote(id);
    }

}

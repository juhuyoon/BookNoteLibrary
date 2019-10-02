package com.example.noteservice.dao;

import com.example.noteservice.dto.Note;

import java.util.List;

public interface NoteDao {

    Note addNote(Note note);

    Note getNote(Integer noteId);

    List<Note> getAllNotes();

    void updateNote(Note note);

    void deleteNote(Integer noteId);

    List<Note> getNotesByBookId(Integer bookId);
}

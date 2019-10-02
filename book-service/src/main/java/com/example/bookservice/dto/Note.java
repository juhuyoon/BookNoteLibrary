package com.example.bookservice.dto;

import java.util.Objects;

public class Note {
    private int note_id;
    private int book_id;
    private String note;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return note_id == note1.note_id &&
                book_id == note1.book_id &&
                note.equals(note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note_id, book_id, note);
    }
}

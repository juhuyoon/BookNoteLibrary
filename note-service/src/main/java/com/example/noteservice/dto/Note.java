package com.example.noteservice.dto;

import java.util.Objects;

public class Note {

    private Integer id;
    private Integer bookId;
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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
        return id.equals(note1.id) &&
                bookId.equals(note1.bookId) &&
                Objects.equals(note, note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, note);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }
}

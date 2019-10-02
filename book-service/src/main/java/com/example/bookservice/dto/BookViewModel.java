package com.example.bookservice.dto;

import java.util.List;
import java.util.Objects;

public class BookViewModel {

    private int bookId;
    private String title;
    private String author;
    private String note;
    private List notesList;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List getNotesList() {
        return notesList;
    }
    public void setNotesList(List notes) {
        this.notesList = notes;
    }

    public void setNote(String note) {
        this.note = note;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookViewModel that = (BookViewModel) o;
        return bookId == that.bookId &&
                title.equals(that.title) &&
                author.equals(that.author) &&
                note.equals(that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, note);
    }
}

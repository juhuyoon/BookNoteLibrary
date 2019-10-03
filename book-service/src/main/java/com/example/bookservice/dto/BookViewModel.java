package com.example.bookservice.dto;

import java.util.List;

public class BookViewModel {

    private int bookId;
    private String title;
    private String author;
    private List<Note> note;

    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

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

//
//    public List getNote() {
//        return note;
//    }
//
//    public void setNote(List note) {
//        this.note = note;
//    }
}

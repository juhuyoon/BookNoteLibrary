package com.example.bookservice.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("note-service")
public interface NoteServerClient {

    @GetMapping(value = "/notes/book/{book_id}")
    public List getNotesByBook(@PathVariable int book_id);

    @DeleteMapping("{id}")
    public void deleteNotes(@PathVariable int id);
}

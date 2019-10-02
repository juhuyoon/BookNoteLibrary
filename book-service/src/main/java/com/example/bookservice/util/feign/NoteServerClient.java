package com.example.bookservice.util.feign;

import com.example.bookservice.dto.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("note-service")
public interface NoteServerClient {

    @GetMapping(value = "/notes/book/{book_id}")
    public List getNotesByBook(@PathVariable int book_id);

    @PostMapping(value = "/notes")
    public Note postNotes(Note note);

    @PutMapping(value = "/{id}")
    public Note updateNotes(@PathVariable int id);

    @DeleteMapping("{id}")
    public void deleteNotes(@PathVariable int id);
}

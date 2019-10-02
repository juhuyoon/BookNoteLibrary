package com.example.bookservice.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("note-service")
public interface NoteServerClient {
    @GetMapping(value = "/notes/{id}")
    public List getNotesByBook();

//    @PostMapping(value = "/notes")
//    public Object postNotes(Note note);
}

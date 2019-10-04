package com.example.notequeueconsumer.util.feign;

import com.example.notequeueconsumer.util.messages.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "note-service")
public interface NoteServiceClient {
    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note addNote(@RequestBody Note note);

//    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
//    public Note getNoteById(@PathVariable(name="id") Integer id);
//
//    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
//    public List<Note> getNotesByBookId(@PathVariable(name="book_id") Integer bookId);
//
//    @RequestMapping(value = "/notes", method = RequestMethod.GET)
//    public List<Note> getAllNotes();

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public void updateNote(@RequestBody Note note, @PathVariable(name="id") Integer id);

//    @RequestMapping(value = "/notes", method = RequestMethod.DELETE)
//    public void deleteNote(@PathVariable(name = "id") Integer id);
}

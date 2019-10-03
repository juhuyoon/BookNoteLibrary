package com.example.notequeueconsumer;

import com.example.notequeueconsumer.util.feign.NoteServiceClient;
import com.example.notequeueconsumer.util.messages.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private final NoteServiceClient client;

    MessageListener(NoteServiceClient client) {
        this.client = client;
    }

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(Note note) {
        if (note.getNoteId() == 0) {
            client.addNote(note);
        } else {
            client.updateNote(note, note.getNoteId());
        }
    }
}
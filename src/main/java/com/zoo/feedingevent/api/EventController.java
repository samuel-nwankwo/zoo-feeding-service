package com.zoo.feedingevent.api;

import com.zoo.feedingevent.model.Event;
import com.zoo.feedingevent.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    private final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}

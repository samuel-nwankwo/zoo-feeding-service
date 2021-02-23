package com.zoo.feedingevent.api;
import com.zoo.feedingevent.exception.InvalidEntryException;
import com.zoo.feedingevent.exception.NoEntityFoundException;
import com.zoo.feedingevent.model.Event;
import com.zoo.feedingevent.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(NoEntityFoundException::new);
    }
    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@Validated @RequestBody Event event) throws URISyntaxException {
        try {
            log.info("Request to create event: {}", event);
            Event result = eventRepository.save(event);
            return ResponseEntity.created(new URI("/event/" + result.getId()))
                    .body(result);
        }catch(JpaObjectRetrievalFailureException e){
            throw new InvalidEntryException();
        }
    }
    @PutMapping("/event/{id}")
    public ResponseEntity<Event> updateEvent(@Validated @PathVariable Long id, @RequestBody Event event) {
        log.info("Request to update event: {}", event);
        try {
            if (eventRepository.findById(id).isPresent()) {
                Event result = eventRepository.save(event);
                return ResponseEntity.ok().body(result);
            } else {
                throw new NoEntityFoundException();
            }
        }catch(JpaObjectRetrievalFailureException e){
            throw new InvalidEntryException();
        }
    }
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        log.info("Request to delete event: {}", id);
        if(eventRepository.findById(id).isPresent()){
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();
        }else{
            throw new NoEntityFoundException();
        }
    }
}

package com.zoo.feedingevent;

import com.zoo.feedingevent.repository.EventRepository;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    private final EventRepository repository;

    public Initializer(EventRepository repository) {
        this.repository = repository;
    }
}

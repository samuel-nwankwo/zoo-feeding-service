package com.zoo.feedingevent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.model.Event;
import com.zoo.feedingevent.repository.EventRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;

    private static final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> eventList = new ArrayList<>();
        Event event1 = new Event(1L,"Listing all events");
        eventList.add(event1);
        Mockito.when(eventRepository.findAll()).thenReturn(eventList);
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].title", Matchers.equalTo("Listing all events")));
    }
    @Test
    public void testGetEventById() throws Exception {
        Event event2 = new Event(1L,"Finding an event by id");
        Mockito.when(eventRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(event2));
        mockMvc.perform((MockMvcRequestBuilders.get("/event/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.equalTo("Finding an event by id")));
    }
    @Test
    public void testGetEventThatDoesNotExist() throws Exception {
        Mockito.when(eventRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        mockMvc.perform((MockMvcRequestBuilders.get("/event/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isNotFound());
    }
    @Test
    public void testCreateEvent() throws Exception {
        Event event3 = new Event(1L,"Creating an event");
        Mockito.when(eventRepository.save(ArgumentMatchers.any())).thenReturn(event3);
        String json = mapper.writeValueAsString(event3);
        mockMvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status()
                .isCreated())
                .andExpect(jsonPath("$.title", Matchers.equalTo("Creating an event")));
    }
    @Test
    public void testEventWithInvalidInput() throws Exception {
        Set<Animal> animals = new HashSet<>();
        animals.add(new Animal(1L,"Jack","kangaroo"));
        Event event = new Event(1L,"Creating an event");
        event.setAnimals(animals);
        Mockito.when(eventRepository.save(ArgumentMatchers.any())).thenThrow(
                new JpaObjectRetrievalFailureException(new EntityNotFoundException()));
        String json = mapper.writeValueAsString(event);
        mockMvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status()
                .isBadRequest());
    }
    @Test
    public void testUpdateEvent() throws Exception {
        Event event = new Event(1L,"Updating an event");
        Mockito.when(eventRepository.save(ArgumentMatchers.any())).thenReturn(event);
        Mockito.when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));
        String json = mapper.writeValueAsString(event);
        mockMvc.perform((MockMvcRequestBuilders.put("/event/{id}","1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Updating an event")));
    }
    @Test
    public void testDeleteEvent() throws Exception {
        Event event = new Event(1L,"Delete an event");
        Mockito.when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        Mockito.doNothing().when(eventRepository).deleteById(event.getId());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/event/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

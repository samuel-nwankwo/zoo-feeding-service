package com.zoo.feedingevent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Event event1 = new Event("A new feeding event");
        eventList.add(event1);
        Mockito.when(eventRepository.findAll()).thenReturn(eventList);
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].title", Matchers
                .equalTo("A new feeding event")));
    }
    @Test
    public void testGetEventById() throws Exception {
         Event event2 = new Event("An old feeding event");

        Mockito.when(eventRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(event2));
        mockMvc.perform((MockMvcRequestBuilders.get("/event/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.equalTo("An old feeding event")));
    }
}

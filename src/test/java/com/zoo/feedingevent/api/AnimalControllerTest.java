package com.zoo.feedingevent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.repository.AnimalRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository animalRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllAnimals() throws Exception {
        List<Animal> animalList = new ArrayList<>();
        Animal lion = new Animal("lion");
        animalList.add(lion);
        Mockito.when(animalRepository.findAll()).thenReturn(animalList);
        mockMvc.perform(get("/animal")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].breed", Matchers.equalTo("lion")));

    }

    @Test
    public void testGetAnimalById() throws Exception {
        Animal lion = new Animal(1L,"simba","lion");

        Mockito.when(animalRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(lion));
        mockMvc.perform((MockMvcRequestBuilders.get("/animal/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("simba")))
                .andExpect(jsonPath("$.breed", Matchers.equalTo("lion")));
    }
    @Test
    public void testCreateAnimal() throws Exception {
        Animal mufasa = new Animal(1L, "mufasa","lion");
        Mockito.when(animalRepository.save(ArgumentMatchers.any())).thenReturn(mufasa);
        String json = mapper.writeValueAsString(mufasa);
        mockMvc.perform(post("/animal").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status()
                .isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("mufasa")))
                .andExpect(jsonPath("$.breed", Matchers.equalTo("lion")));
    }
}

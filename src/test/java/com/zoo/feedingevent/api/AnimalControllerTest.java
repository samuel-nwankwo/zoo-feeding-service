package com.zoo.feedingevent.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
        Animal animal = new Animal("lion");
        animalList.add(animal);
        Mockito.when(animalRepository.findAll()).thenReturn(animalList);
        mockMvc.perform(get("/animal")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].breed", Matchers.equalTo("lion")));

    }
    @Test
    public void testGetAnimalById() throws Exception {
        Animal animal = new Animal(1L,"lion","lion");
        Mockito.when(animalRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(animal));
        mockMvc.perform((MockMvcRequestBuilders.get("/animal/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)));
    }
    @Test
    public void testGetAnimalThatDoesNotExist() throws Exception {
        Mockito.when(animalRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        mockMvc.perform((MockMvcRequestBuilders.get("/animal/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isNotFound());
    }
    @Test
    public void testCreateAnimal() throws Exception {
        Animal animal = new Animal(1L, "lion","lion");
        Mockito.when(animalRepository.save(ArgumentMatchers.any())).thenReturn(animal);
        String json = mapper.writeValueAsString(animal);
        mockMvc.perform(post("/animal").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status()
                .isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)));
    }
    @Test
    public void testUpdateAnimal() throws Exception {
        Animal animal = new Animal(2L, "lion", "lion");
        Mockito.when(animalRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(animal));
        Mockito.when(animalRepository.save(ArgumentMatchers.any())).thenReturn(animal);
        String json = mapper.writeValueAsString(animal);
        mockMvc.perform((MockMvcRequestBuilders.put("/animal/{id}","2")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(2)));
    }
    @Test
    public void testDeleteAnimal() throws Exception {
        Animal animal = new Animal(3L, "lion","lion");
        Mockito.when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        Mockito.doNothing().when(animalRepository).deleteById(animal.getId());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/animal/{id}", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

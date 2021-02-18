package com.zoo.feedingevent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.repository.AnimalRepository;
import com.zoo.feedingevent.repository.FoodRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository animalRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

//    @Test
//    public void testGetAllAnimals() throws Exception {
//        List<Animal> animalList = new ArrayList<>();
//        Animal bessy = new Animal("cow");
//        animalList.add(bessy);
//        Mockito.when(animalRepository.findAll()).thenReturn(animalList);
//        mockMvc.perform(get("/animal")).andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
//                .andExpect(jsonPath("$[0].name", Matchers.equalTo("cow")));
//
//    }
}

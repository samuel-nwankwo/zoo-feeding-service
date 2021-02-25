package com.zoo.feedingevent.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.FoodRepository;
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


@WebMvcTest(FoodController.class)
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodRepository foodRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllFoods() throws Exception {
        List<Food> foodList = new ArrayList<>();
        Food food = new Food(1L,"grass");
        foodList.add(food);
        Mockito.when(foodRepository.findAll()).thenReturn(foodList);
        mockMvc.perform(get("/food")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("grass")));

    }
    @Test
    public void testGetFoodById() throws Exception {
        Food food = new Food(1L,"grass");
        Mockito.when(foodRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(food));
        mockMvc.perform((MockMvcRequestBuilders.get("/food/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("grass")));
    }
    @Test
    public void testGetFoodThatDoesNotExist() throws Exception {
        Mockito.when(foodRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        mockMvc.perform((MockMvcRequestBuilders.get("/food/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFood() throws Exception {
        Food food = new Food(1L, "grass");
        Mockito.when(foodRepository.save(ArgumentMatchers.any())).thenReturn(food);
        String json = mapper.writeValueAsString(food);
        mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status()
                .isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("grass")));
    }
    @Test
    public void testUpdateFood() throws Exception {
        Food food = new Food(2L, "hay");
        Mockito.when(foodRepository.save(ArgumentMatchers.any())).thenReturn(food);
        Mockito.when(foodRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(food));
        String json = mapper.writeValueAsString(food);
        mockMvc.perform((MockMvcRequestBuilders.put("/food/{id}","2")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("hay")));
    }
    @Test
    public void testDeleteFood() throws Exception {
        Food food = new Food(1L, "hay");
        Mockito.when(foodRepository.findById(food.getId())).thenReturn(Optional.of(food));
        Mockito.doNothing().when(foodRepository).deleteById(food.getId());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/food/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

package com.zoo.feedingevent.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.zoo.feedingevent.model.Food;
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


@WebMvcTest
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodRepository foodRepository;

    @Test
    public void testGetAllFoods() throws Exception {
        List<Food> foodList = new ArrayList<>();
        Food grass = new Food(1L,"grass");
        Food hay = new Food(2L,"hay");

        foodList.add(grass);
        foodList.add(hay);
        Mockito.when(foodRepository.findAll()).thenReturn(foodList);
        mockMvc.perform(get("/food")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("grass")));
    }
}

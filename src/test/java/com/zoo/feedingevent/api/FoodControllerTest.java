package com.zoo.feedingevent.api;

import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.FoodRepository;
import org.junit.jupiter.api.Test;
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
    public void testGetAllFoods(){
        List<Food> foodList = new ArrayList<>();
        Food grass = new Food();
        Food hay = new Food();
//        foodList.add(grass);
    }
}

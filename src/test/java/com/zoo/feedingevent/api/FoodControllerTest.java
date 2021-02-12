package com.zoo.feedingevent.api;

import com.zoo.feedingevent.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodRepository foodRepository;

}

package com.zoo.feedingevent.api;

import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class FoodController{
    private final Logger log = LoggerFactory.getLogger(FoodController.class);
    private FoodRepository foodRepository;


    public FoodController(FoodRepository foodRepository) {
        this.foodRepository= foodRepository;
    }

    @PostMapping("/food")
    ResponseEntity<Food> createFood(@Validated @RequestBody Food food) throws URISyntaxException {
        log.info("Request to create food: {}", food);
        Food result = foodRepository.save(food);
        return ResponseEntity.created(new URI("/food/" + result.getId()))
                .body(result);
    }
}

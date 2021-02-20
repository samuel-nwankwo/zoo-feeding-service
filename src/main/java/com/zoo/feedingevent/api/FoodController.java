package com.zoo.feedingevent.api;

import com.zoo.feedingevent.exception.EntityIsReferencedException;
import com.zoo.feedingevent.exception.EntityNotFoundException;
import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FoodController{
    private final Logger log = LoggerFactory.getLogger(FoodController.class);
    private final FoodRepository foodRepository;


    public FoodController(FoodRepository foodRepository) {
        this.foodRepository= foodRepository;
    }

    @GetMapping("/food")
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = new ArrayList<>();
        foodRepository.findAll().forEach(foods::add);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    @GetMapping("/food/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        Optional<Food> food = foodRepository.findById(id);
        return food.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/food")
    public ResponseEntity<Food> createFood(@Validated @RequestBody Food food) throws URISyntaxException {
        log.info("Request to create food: {}", food);
        Food result = foodRepository.save(food);
        return ResponseEntity.created(new URI("/food/" + result.getId()))
                .body(result);
    }
    @PutMapping("/food/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable(value = "id") Long id, @Validated @RequestBody Food food) {
        log.info("Request to update food: {}", food);
        if(foodRepository.findById(id).isPresent()) {
            Food result = foodRepository.save(food);
            return ResponseEntity.ok().body(result);
        }else{
            throw new EntityNotFoundException();
        }
    }
    @DeleteMapping("/food/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        log.info("Request to delete food: {}", id);
        Optional<Food> food = foodRepository.findById(id);
        try{
            if (food.isPresent()) {
                foodRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                throw new EntityNotFoundException();
            }
        } catch (DataIntegrityViolationException e) {
            log.error("System error: {}", e.getMessage());
            throw new EntityIsReferencedException();
        }
    }

}

package com.zoo.feedingevent.api;

import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.repository.AnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AnimalController {

    private final Logger log = LoggerFactory.getLogger(AnimalController.class);
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("/animal")
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        animalRepository.findAll().forEach(animals::add);
        return new ResponseEntity<>(animals, HttpStatus.OK);

    }
    @GetMapping("/animal/{id}")
    public ResponseEntity<?> getAnimalById(@PathVariable Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/animal")
    public ResponseEntity<Animal> createFood(@Validated @RequestBody Animal animal) throws URISyntaxException {
        log.info("Request to create animal: {}", animal);
        Animal result = animalRepository.save(animal);
        return ResponseEntity.created(new URI("/animal/" + result.getId()))
                .body(result);
    }
}

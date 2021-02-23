package com.zoo.feedingevent.api;

import com.zoo.feedingevent.exception.EntityIsReferencedException;
import com.zoo.feedingevent.exception.NoEntityFoundException;
import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.repository.AnimalRepository;
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
                .orElseThrow(NoEntityFoundException::new);
    }
    @PostMapping("/animal")
    public ResponseEntity<Animal> createAnimal(@Validated @RequestBody Animal animal) throws URISyntaxException {
        log.info("Request to create animal: {}", animal);
        Animal result = animalRepository.save(animal);
        return ResponseEntity.created(new URI("/animal/" + result.getId()))
                .body(result);
    }
    @PutMapping("/animal/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable(value = "id") Long id, @Validated @RequestBody Animal animal) {
        log.info("Request to update animal: {}", animal);
        if(animalRepository.findById(id).isPresent()) {
            Animal result = animalRepository.save(animal);
            return ResponseEntity.ok().body(result);
        }else{
            throw new NoEntityFoundException();
        }
    }
    @DeleteMapping("/animal/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
        log.info("Request to delete animal: {}", id);
        Optional<Animal> animal = animalRepository.findById(id);
        try {
            if (animal.isPresent()) {
                animalRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                throw new NoEntityFoundException();
            }
        }catch(DataIntegrityViolationException e){
            log.error("System error: {}",e.getMessage());
            throw new EntityIsReferencedException();
        }

    }
}

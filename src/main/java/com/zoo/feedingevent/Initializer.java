package com.zoo.feedingevent;

import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final FoodRepository repository;

    public Initializer(FoodRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Stream.of("grass", "seeds", "hay",
                "corn silage","grain").forEach(name ->
                repository.save(new Food(name))
        );

        repository.findAll().forEach(System.out::println);
    }
}

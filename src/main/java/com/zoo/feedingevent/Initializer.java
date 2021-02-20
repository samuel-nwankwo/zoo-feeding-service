package com.zoo.feedingevent;

import com.zoo.feedingevent.model.Animal;
import com.zoo.feedingevent.model.Event;
import com.zoo.feedingevent.model.Food;
import com.zoo.feedingevent.repository.AnimalRepository;
import com.zoo.feedingevent.repository.EventRepository;
import com.zoo.feedingevent.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


@Component
public class Initializer implements CommandLineRunner {

    private final FoodRepository foodRepository;
    private final AnimalRepository animalRepository;
    private final EventRepository eventRepository;

    public Initializer(FoodRepository foodRepository, AnimalRepository animalRepository, EventRepository eventRepository) {
        this.foodRepository = foodRepository;
        this.animalRepository = animalRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        Stream.of(
                new Food(1L,"grass"), new Food(2L,"hay"),
                new Food(3L,"chicken"), new Food(4L, "steak")
                ).forEach(foodRepository::save);
        Set<Food> veggies = new HashSet<>();
        Set<Food> meats = new HashSet<>();
        veggies.add(foodRepository.findById(1L).orElse(null));
        veggies.add(foodRepository.findById(2L).orElse(null));
        meats.add(foodRepository.findById(3L).orElse(null));
        meats.add(foodRepository.findById(4L).orElse(null));

        Stream.of(
                new Animal(1L,"zebra","zebra"),
                new Animal(2L,"zebra","zebra"),
                new Animal(3L, "lion1", "lion"),
                new Animal(3L, "lion2", "lion")
        ).forEach(animalRepository::save);
        Set<Animal> zebras = new HashSet<>();
        Set<Animal> lions = new HashSet<>();
        zebras.add(animalRepository.findById(1L).orElse(null));
        zebras.add(animalRepository.findById(2L).orElse(null));
        lions.add(animalRepository.findById(3L).orElse(null));
        lions.add(animalRepository.findById(4L).orElse(null));

        Event event1 = Event.builder()
                .id(1L)
                .date(Instant.now())
                .title("Feeding the zebras")
                .description("Fed the zebras breakfast")
                .foods(veggies)
                .animals(zebras)
                .build();
        eventRepository.save(event1);

        Event event2 = Event.builder()
                .id(2L)
                .date(Instant.now())
                .title("Feeding the lions")
                .description("Fed the lions breakfast")
                .foods(meats)
                .animals(lions)
                .build();
        eventRepository.save(event1);
    }

}

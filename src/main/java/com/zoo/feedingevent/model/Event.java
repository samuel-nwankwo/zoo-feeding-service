package com.zoo.feedingevent.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private String title;
    private String description;
    @ManyToMany
    private Set<Food> foods;
    @ManyToMany
    private Set<Animal> animals;

}

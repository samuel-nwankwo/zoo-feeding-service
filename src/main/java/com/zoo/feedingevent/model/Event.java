package com.zoo.feedingevent.model;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    @NonNull
    private String title;
    private String description;
    @ManyToMany
    private Set<Food> foods;
    @ManyToMany
    private Set<Animal> animals;

}

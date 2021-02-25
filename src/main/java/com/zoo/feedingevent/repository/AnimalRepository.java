package com.zoo.feedingevent.repository;

import com.zoo.feedingevent.model.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal,Long>{
}

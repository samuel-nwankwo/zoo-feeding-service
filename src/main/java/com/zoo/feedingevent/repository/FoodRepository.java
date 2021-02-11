package com.zoo.feedingevent.repository;

import com.zoo.feedingevent.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food,Long> {}

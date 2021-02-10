package com.zoo.feedingevent.repository;

import com.zoo.feedingevent.model.Food;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository  extends CrudRepository<Food,Long> {}

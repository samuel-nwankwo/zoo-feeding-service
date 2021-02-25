package com.zoo.feedingevent.repository;

import com.zoo.feedingevent.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event,Long>{
}

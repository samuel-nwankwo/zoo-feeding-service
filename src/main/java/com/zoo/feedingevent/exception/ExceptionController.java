package com.zoo.feedingevent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = AnimalNotFoundException.class)
    public ResponseEntity<Object> exception(AnimalNotFoundException exception){
        return new ResponseEntity<>("The animal at specified id could not be found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = FoodNotFoundException.class)
    public ResponseEntity<Object> exception(FoodNotFoundException exception){
        return new ResponseEntity<>("The food at specified id could not be found", HttpStatus.NOT_FOUND);
    }
}

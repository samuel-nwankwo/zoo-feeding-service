package com.zoo.feedingevent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> exception(EntityNotFoundException exception){
        return new ResponseEntity<>("The entity at specified id could not be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityIsReferencedException.class)
    public ResponseEntity<Object> exception(EntityIsReferencedException exception){
        return new ResponseEntity<>("All references to specified entity should be deleted first", HttpStatus.BAD_REQUEST);
    }

}

package com.zoo.feedingevent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoEntityFoundException.class)
    public ResponseEntity<Object> exception(NoEntityFoundException exception){
        return new ResponseEntity<>("The entity at specified id could not be found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = EntityIsReferencedException.class)
    public ResponseEntity<Object> exception(EntityIsReferencedException exception){
        return new ResponseEntity<>("All references to specified entity should be deleted first", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = InvalidEntryException.class)
    public ResponseEntity<Object> exception(InvalidEntryException exception){
        return new ResponseEntity<>("One or more of the fields entered are incorrect", HttpStatus.BAD_REQUEST);
    }
}

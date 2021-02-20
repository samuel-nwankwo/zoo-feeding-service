package com.zoo.feedingevent.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AnimalNotFoundException.class)
    public ResponseEntity<Object> exception(AnimalNotFoundException exception){
        return new ResponseEntity<>("The animal at specified id could not be found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = FoodNotFoundException.class)
    public ResponseEntity<Object> exception(FoodNotFoundException exception){
        return new ResponseEntity<>("The food at specified id could not be found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = AnimalIsReferencedException.class)
    public ResponseEntity<Object> exception(AnimalIsReferencedException exception){
        return new ResponseEntity<>("All references to specified animal should be deleted first", HttpStatus.NOT_FOUND);
    }

}

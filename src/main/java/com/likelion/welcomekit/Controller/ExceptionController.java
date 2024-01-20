package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Exception.EntityNotBabyLionException;
import com.likelion.welcomekit.Exception.EntityNotManagerException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntitynotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage()+"가 발견되지 않았습니다.");
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<String> handleEntityExistsException(Exception e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage()+"가 이미 존재합니다.");
    }

    @ExceptionHandler({EntityNotManagerException.class, EntityNotBabyLionException.class})
    public ResponseEntity<?> handleUserTypeNotMatch(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    // 기본 Exception 영역
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntitynotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage()+"(이)가 발견되지 않았습니다.");
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<String> handleEntityExistsException(Exception e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage()+"(이)가 이미 존재합니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleValidationExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 인자가 입력되었습니다.");
    }

    @ExceptionHandler({ExpiredJwtException.class, SignatureException.class})
    public ResponseEntity<String> handleJwtException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("세션이 만료되었습니다. 다시 로그인 바랍니다.");
    }

    // Custom Exception 영역
    @ExceptionHandler({
            EntityNotManagerException.class, EntityNotBabyLionException.class,
            InvalidPasswordException.class,
            EntityDuplicatedException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({InvalidDBException.class})
    public ResponseEntity<?> handleInternalServerException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(AnyExceptionsWithResponse.class)
    public ResponseEntity<String> handleAnyExceptionsWithResponse(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}

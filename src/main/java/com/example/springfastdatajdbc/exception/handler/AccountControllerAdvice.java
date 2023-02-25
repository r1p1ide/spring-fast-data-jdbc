package com.example.springfastdatajdbc.exception.handler;

import com.example.springfastdatajdbc.exception.AccountNotFoundException;
import com.example.springfastdatajdbc.util.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDetails> accountNotFoundExceptionHandler() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails(400, LocalDateTime.now(), false, "Wrong id"));
    }
}

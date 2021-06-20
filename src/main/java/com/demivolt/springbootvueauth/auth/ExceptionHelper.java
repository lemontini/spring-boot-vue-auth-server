package com.demivolt.springbootvueauth.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NonUniqueResultException;

@ControllerAdvice
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex) {
        logger.error("Credentials already used: ", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}

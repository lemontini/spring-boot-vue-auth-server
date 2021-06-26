package com.demivolt.springbootvueauth.auth;

import com.demivolt.springbootvueauth.entity.Response;
import com.demivolt.springbootvueauth.entity.User;
import com.demivolt.springbootvueauth.exception.DuplicateUserException;
import com.demivolt.springbootvueauth.exception.NoAccessException;
import com.demivolt.springbootvueauth.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> newUser(@RequestBody User user) throws DuplicateUserException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> loginUser(@RequestBody User user) throws UserNotFoundException {
        return new ResponseEntity<>(userService.authenticateUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> protectedResource(@RequestParam String token) throws NoAccessException {
        return new ResponseEntity<>(userService.accessResource(token), HttpStatus.OK);
    }

}

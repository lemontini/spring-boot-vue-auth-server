package com.demivolt.springbootvueauth.auth;

import com.demivolt.springbootvueauth.entity.AuthenticationError;
import com.demivolt.springbootvueauth.entity.Response;
import com.demivolt.springbootvueauth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> newUser(@RequestBody User user) {

        Response savedUser = userService.createUser(user);
        if (savedUser != null) return new ResponseEntity(savedUser, HttpStatus.OK);
        else return new ResponseEntity(new AuthenticationError(401, "Error with user creation"), HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(@RequestBody User user) {

        Response authenticatedUser = userService.authenticateUser(user);
        if (authenticatedUser != null) return new ResponseEntity(authenticatedUser, HttpStatus.OK);
        else return new ResponseEntity("Invalid credentials", HttpStatus.UNAUTHORIZED);

    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/")
    public ResponseEntity protectedResource(@RequestParam String token) {

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        if (optionalConfirmationToken.isPresent()) {
            userService.accessResource(); // function to proceed to the protected resource
            return new ResponseEntity("Access granted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Invalid token", HttpStatus.UNAUTHORIZED);
        }

    }

}

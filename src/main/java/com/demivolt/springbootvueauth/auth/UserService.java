package com.demivolt.springbootvueauth.auth;

import com.demivolt.springbootvueauth.entity.AuthenticationError;
import com.demivolt.springbootvueauth.entity.Response;
import com.demivolt.springbootvueauth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public Response createUser(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new NonUniqueResultException("credentials are already used");
            // return new AuthenticationError(401, "Given username or email is already in use");
        }

        User savedUser = userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenRepository.save(confirmationToken);
        Response response = new Response(savedUser.getUsername(), confirmationToken.getConfirmationToken());
        System.out.println("New user created: " + savedUser.getUsername() + "; token: " + confirmationToken.getConfirmationToken());
        return response;

    }

    public Response authenticateUser(User user) {
        Optional<User> queryUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (queryUser.isPresent()) {
            Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByUserId(queryUser.get().getId());
            if (optionalConfirmationToken.isPresent()) {
                ConfirmationToken confirmationToken = optionalConfirmationToken.get();
                Response response = new Response(confirmationToken.getUser().getUsername(), confirmationToken.getConfirmationToken());
                System.out.println(response.getUsername() + ", " + response.getAccess_token());
                return response;
            }
        }
        return null;
    }

    public void accessResource() {
        System.out.println("Protected resource accessed.");
    }
}

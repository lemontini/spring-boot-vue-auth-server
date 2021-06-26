package com.demivolt.springbootvueauth.auth;

import com.demivolt.springbootvueauth.entity.Response;
import com.demivolt.springbootvueauth.entity.User;
import com.demivolt.springbootvueauth.exception.DuplicateUserException;
import com.demivolt.springbootvueauth.exception.NoAccessException;
import com.demivolt.springbootvueauth.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public Response createUser(User user) throws DuplicateUserException {

        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw DuplicateUserException.createFrom(user);
        }

        User savedUser = userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenRepository.save(confirmationToken);
        Response response = new Response(savedUser.getUsername(), confirmationToken.getConfirmationToken());
        System.out.println("New user created: " + savedUser.getUsername() + "; token: " + confirmationToken.getConfirmationToken());
        return response;

    }

    public Response authenticateUser(User user) throws UserNotFoundException {
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
        throw new UserNotFoundException();
    }

    public Object accessResource(String token) throws NoAccessException {
        System.out.println("Protected resource accessed.");

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        if (optionalConfirmationToken.isPresent()) {
            return "Access granted";
        }

        throw new NoAccessException();
    }
}

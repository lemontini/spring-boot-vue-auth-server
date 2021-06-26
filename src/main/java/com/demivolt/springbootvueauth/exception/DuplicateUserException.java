package com.demivolt.springbootvueauth.exception;

import com.demivolt.springbootvueauth.entity.User;

public class DuplicateUserException extends Exception{

    private User user;

    public static DuplicateUserException createFrom(User user) {
        return new DuplicateUserException(user);
    }

    public DuplicateUserException(User user) {
        this.user = user;
    }

    @Override
    public String getMessage() {
        return "Email '" + user.getEmail() + "' is already registered";
    }

}

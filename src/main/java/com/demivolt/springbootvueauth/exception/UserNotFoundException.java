package com.demivolt.springbootvueauth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Bad credentials";
    }
}

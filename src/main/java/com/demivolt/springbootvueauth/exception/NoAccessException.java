package com.demivolt.springbootvueauth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoAccessException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid token";
    }
}

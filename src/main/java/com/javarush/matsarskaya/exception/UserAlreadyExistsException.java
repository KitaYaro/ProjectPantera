package com.javarush.matsarskaya.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("A user named '\" username + \"' already exists");
    }

    public UserAlreadyExistsException(String username, Throwable cause) {
        super("A user named '\" username + \"' already exists", cause);
    }
}

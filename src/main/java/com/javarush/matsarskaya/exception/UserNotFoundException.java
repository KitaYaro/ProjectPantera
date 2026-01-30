package com.javarush.matsarskaya.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("The user named '\" username + \"' was not found");
  }

  public UserNotFoundException(String username, Throwable cause) {
    super("The user named '\" username + \"' was not found", cause);
  }
}

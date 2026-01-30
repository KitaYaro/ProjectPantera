package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for UserAlreadyExistsException")
class UserAlreadyExistsExceptionTest {

    @Test
    @DisplayName("Creating an exception with a username")
    void testExceptionWithUsername() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("testuser");

        assertThat(exception.getMessage()).contains("username");
        assertThat(exception.getMessage()).contains("already exists");
    }

    @Test
    @DisplayName("Creating an exception with a username and reason")
    void testExceptionWithUsernameAndCause() {
        Throwable cause = new RuntimeException("Database error");
        UserAlreadyExistsException exception = new UserAlreadyExistsException("testuser", cause);

        assertThat(exception.getMessage()).contains("username");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}

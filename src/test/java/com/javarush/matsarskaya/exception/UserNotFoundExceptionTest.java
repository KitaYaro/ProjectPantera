package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for UserNotFoundException")
class UserNotFoundExceptionTest {

    @Test
    @DisplayName("Creating an exception with a username")
    void testExceptionWithUsername() {
        UserNotFoundException exception = new UserNotFoundException("testuser");

        assertThat(exception.getMessage()).contains("username");
        assertThat(exception.getMessage()).contains("not found");
    }

    @Test
    @DisplayName("Creating an exception with a username and reason")
    void testExceptionWithUsernameAndCause() {
        Throwable cause = new RuntimeException("Database error");
        UserNotFoundException exception = new UserNotFoundException("testuser", cause);

        assertThat(exception.getMessage()).contains("username");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}

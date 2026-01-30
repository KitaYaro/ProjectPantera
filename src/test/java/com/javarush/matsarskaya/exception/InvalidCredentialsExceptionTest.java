package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for InvalidCredentialsException")
class InvalidCredentialsExceptionTest {

    @Test
    @DisplayName("Creating an exception without a message")
    void testExceptionWithoutMessage() {
        InvalidCredentialsException exception = new InvalidCredentialsException();

        assertThat(exception.getMessage()).contains("Invalid username or password");
    }

    @Test
    @DisplayName("Creating an exception with a message")
    void testExceptionWithMessage() {
        String customMessage = "Custom error message";
        InvalidCredentialsException exception = new InvalidCredentialsException(customMessage);

        assertThat(exception.getMessage()).isEqualTo(customMessage);
    }
}

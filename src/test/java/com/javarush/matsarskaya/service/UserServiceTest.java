package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UserService")
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Successful registration of a new user")
    void testRegisterUserSuccess() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(true);

        userService.registerUser("newuser", "password123");

        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Registering an existing user throws an exception")
    void testRegisterUserAlreadyExists() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUser("existinguser", "password123"))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("username");

        verify(userRepository).existsByUsername("existinguser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Successful user login")
    void testLoginUserSuccess() {
        User user = new User("testuser", "password123");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.loginUser("testuser", "password123");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Logging in with a non-existent user throws an exception")
    void testLoginUserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loginUser("nonexistent", "password123"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("username");

        verify(userRepository).findByUsername("nonexistent");
    }

    @Test
    @DisplayName("Logging in with an incorrect password throws an exception.")
    void testLoginUserInvalidPassword() {
        User user = new User("testuser", "correctpassword");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.loginUser("testuser", "wrongpassword"))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(userRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Checking authorization without a session")
    void testIsAuthenticatedWithoutSession() {
        when(request.getSession(false)).thenReturn(null);

        boolean result = UserService.isAuthenticated(request);

        assertThat(result).isFalse();
        verify(request).getSession(false);
    }

    @Test
    @DisplayName("Verifying authorization with a session without username")
    void testIsAuthenticatedWithSessionWithoutUsername() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(null);

        boolean result = UserService.isAuthenticated(request);

        assertThat(result).isFalse();
        verify(request).getSession(false);
        verify(session).getAttribute("username");
    }

    @Test
    @DisplayName("Successful logout")
    void testLogoutSuccess() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");

        userService.logout(request);

        verify(request).getSession(false);
        verify(session).getAttribute("username");
        verify(session).invalidate();
    }

    @Test
    @DisplayName("Log out without an active session")
    void testLogoutWithoutSession() {
        when(request.getSession(false)).thenReturn(null);

        userService.logout(request);

        verify(request).getSession(false);
        verify(session, never()).invalidate();
    }
}

package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.UserService;
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
import static org.mockito.Mockito.*;
import static com.javarush.matsarskaya.config.ApplicationConstants.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for LoginPage")
class LoginPageTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(userService);
    }

    @Test
    @DisplayName("The GET request returns the path to the login page")
    void testDoGet() {
        String result = loginPage.doGet(request);

        assertThat(result).isEqualTo(VIEW_LOGIN);
    }

    @Test
    @DisplayName("Successful user login")
    void testDoPostSuccess() {
        when(request.getParameter(PARAM_USERNAME)).thenReturn("testuser");
        when(request.getParameter(PARAM_PASSWORD)).thenReturn("password123");
        when(userService.loginUser("testuser", "password123"))
                .thenReturn(Optional.of(new User("testuser", "password123")));
        when(request.getSession()).thenReturn(session);

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo(PATH_HOME);
        verify(userService).loginUser("testuser", "password123");
        verify(request).getSession();
        verify(session).setAttribute(SESSION_USERNAME, "testuser");
    }

    @Test
    @DisplayName("Login with a non-existent user")
    void testDoPostUserNotFound() {
        when(request.getParameter(PARAM_USERNAME)).thenReturn("nonexistent");
        when(request.getParameter(PARAM_PASSWORD)).thenReturn("password123");
        when(userService.loginUser("nonexistent", "password123"))
                .thenThrow(new UserNotFoundException("nonexistent"));

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo(VIEW_LOGIN);
        verify(request).setAttribute("error", ERROR_USER_NOT_FOUND);
        verify(userService).loginUser("nonexistent", "password123");
    }

    @Test
    @DisplayName("Log in with an incorrect password")
    void testDoPostInvalidPassword() {
        when(request.getParameter(PARAM_USERNAME)).thenReturn("testuser");
        when(request.getParameter(PARAM_PASSWORD)).thenReturn("wrongpassword");
        when(userService.loginUser("testuser", "wrongpassword"))
                .thenThrow(new InvalidCredentialsException());

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo(VIEW_LOGIN);
        verify(request).setAttribute("error", ERROR_INVALID_CREDENTIALS);
        verify(userService).loginUser("testuser", "wrongpassword");
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = loginPage.getView();

        assertThat(result).isEqualTo("/WEB-INF/login-page.jsp");
    }
}

package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for RegisterPage")
class RegisterPageTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        registerPage = new RegisterPage(userService);
    }

    @Test
    @DisplayName("The GET request returns the path to the registration page")
    void testDoGet() {
        String result = registerPage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/register-page.jsp");
    }

    @Test
    @DisplayName("Successful registration of a new user")
    void testDoPostSuccess() {
        when(request.getParameter("username")).thenReturn("newuser");
        when(request.getParameter("password")).thenReturn("password123");
        doNothing().when(userService).registerUser("newuser", "password123");
        when(request.getSession()).thenReturn(session);

        String result = registerPage.doPost(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).registerUser("newuser", "password123");
        verify(request).getSession();
        verify(session).setAttribute("username", "newuser");
    }

    @Test
    @DisplayName("Registering an existing user")
    void testDoPostUserAlreadyExists() {
        when(request.getParameter("username")).thenReturn("existinguser");
        when(request.getParameter("password")).thenReturn("password123");
        doThrow(new UserAlreadyExistsException("existinguser"))
            .when(userService).registerUser("existinguser", "password123");

        String result = registerPage.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/register-page.jsp");
        verify(request).setAttribute("error", "The user already exists");
        verify(userService).registerUser("existinguser", "password123");
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = registerPage.getView();

        assertThat(result).isEqualTo("/WEB-INF/register-page.jsp");
    }
}

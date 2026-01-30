package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for LogoutPage")
class LogoutPageTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    private LogoutPage logoutPage;

    @BeforeEach
    void setUp() {
        logoutPage = new LogoutPage(userService);
    }

    @Test
    @DisplayName("The POST request performs a logout")
    void testDoPost() {
        doNothing().when(userService).logout(request);

        String result = logoutPage.doPost(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).logout(request);
    }

    @Test
    @DisplayName("The GET request calls doPost")
    void testDoGet() {
        doNothing().when(userService).logout(request);

        String result = logoutPage.doGet(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).logout(request);
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = logoutPage.getView();

        assertThat(result).isEqualTo("/home-page");
    }
}

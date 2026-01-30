package com.javarush.matsarskaya.cmd;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для HomePage")
class HomePageTest {
    @Mock
    private HttpServletRequest request;

    private HomePage homePage;

    @BeforeEach
    void setUp() {
        homePage = new HomePage();
    }

    @Test
    @DisplayName("The GET request returns the path to the home page")
    void testDoGet() {
        String result = homePage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/home-page.jsp");
    }

    @Test
    @DisplayName("The POST request returns the path to the home page")
    void testDoPost() {
        String result = homePage.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/home-page.jsp");
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = homePage.getView();

        assertThat(result).isEqualTo("/WEB-INF/home-page.jsp");
    }
}

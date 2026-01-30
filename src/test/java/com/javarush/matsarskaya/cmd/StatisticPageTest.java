package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.service.StatisticService;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for StatisticPage")
class StatisticPageTest {
    @Mock
    private StatisticService statisticService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private StatisticPage statisticPage;

    @BeforeEach
    void setUp() {
        statisticPage = new StatisticPage(statisticService);
    }

    @Test
    @DisplayName("GET a request with existing statistics")
    void testDoGetWithStatistic() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        Statistic stat = new Statistic("testuser", 10, 5, 5);
        when(statisticService.getStatistic("testuser")).thenReturn(Optional.of(stat));

        String result = statisticPage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/statistic-page.jsp");
        verify(request).getSession();
        verify(session).getAttribute("username");
        verify(statisticService).getStatistic("testuser");
        verify(request).setAttribute("statistic", stat);
    }

    @Test
    @DisplayName("GET a request without statistics")
    void testDoGetWithoutStatistic() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(statisticService.getStatistic("testuser")).thenReturn(Optional.empty());

        String result = statisticPage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/statistic-page.jsp");
        verify(request).getSession();
        verify(session).getAttribute("username");
        verify(statisticService).getStatistic("testuser");
        verify(request, never()).setAttribute(eq("statistic"), any());
    }

    @Test
    @DisplayName("GET a request without an authorized user")
    void testDoGetWithoutUser() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(null);

        String result = statisticPage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/statistic-page.jsp");
        verify(request).getSession();
        verify(session).getAttribute("username");
        verify(statisticService, never()).getStatistic(anyString());
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = statisticPage.getView();

        assertThat(result).isEqualTo("/WEB-INF/statistic-page.jsp");
    }
}

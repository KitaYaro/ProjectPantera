package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.StatisticService;
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
@DisplayName("Tests for QuestDragon")
class QuestDragonTest {
    @Mock
    private StatisticService statisticService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private QuestDragon questDragon;

    @BeforeEach
    void setUp() {
        questDragon = new QuestDragon(statisticService);
    }

    @Test
    @DisplayName("The GET request returns the path to the quest page")
    void testDoGet() {
        String result = questDragon.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
    }

    @Test
    @DisplayName("The beginning of the quest")
    void testDoPostStartQuest() {
        when(request.getParameter("quest")).thenReturn("the way of the dragon rider");
        when(request.getParameter("stage")).thenReturn(null);
        when(request.getParameter("choice")).thenReturn(null);
        when(request.getParameter("playerNameInput")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(session).setAttribute("stage", 0);
        verify(session).setAttribute("trust", 50);
        verify(session).setAttribute("questFinished", false);
    }

    @Test
    @DisplayName("Transition to stage 1")
    void testDoPostStage0() {
        when(request.getParameter("stage")).thenReturn("0");
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(session).setAttribute("stage", 1);
    }

    @Test
    @DisplayName("Entering the player's name")
    void testDoPostStage1WithName() {
        when(request.getParameter("stage")).thenReturn("1");
        when(request.getParameter("playerNameInput")).thenReturn("PlayerName");
        when(request.getParameter("choice")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(session).setAttribute("playerName", "PlayerName");
        verify(session).setAttribute("stage", 2);
    }

    @Test
    @DisplayName("Moving to the next stage with a choice")
    void testDoPostNextStage() {
        when(request.getParameter("stage")).thenReturn("2");
        when(request.getParameter("choice")).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(session.getAttribute("questFinished")).thenReturn(false);
        when(session.getAttribute("trust")).thenReturn(50);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(session).setAttribute("trust", 60);
        verify(session).setAttribute("stage", 3);
    }

    @Test
    @DisplayName("Defeat at a low level of trust")
    void testDoPostLossCondition() {
        when(request.getParameter("stage")).thenReturn("4");
        when(request.getParameter("choice")).thenReturn("-10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(session.getAttribute("questFinished")).thenReturn(false);
        when(session.getAttribute("trust")).thenReturn(45);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(statisticService).registerLoss("testuser");
        verify(session).setAttribute("questFinished", true);
    }

    @Test
    @DisplayName("Winning with a high level of trust")
    void testDoPostWinCondition() {
        when(request.getParameter("stage")).thenReturn("9");
        when(request.getParameter("choice")).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(session.getAttribute("questFinished")).thenReturn(false);
        when(session.getAttribute("trust")).thenReturn(60);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(session).setAttribute("trust", 70);
        verify(session).setAttribute("stage", 10);
    }

    @Test
    @DisplayName("Winning the final stage with a high level of trust")
    void testDoPostFinalStageWin() {
        when(request.getParameter("stage")).thenReturn("10");
        when(request.getParameter("choice")).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(session.getAttribute("questFinished")).thenReturn(false);
        when(session.getAttribute("trust")).thenReturn(70);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
        verify(statisticService).registerWin("testuser");
        verify(session).setAttribute("questFinished", true);
        verify(session).setAttribute("stage", 11);
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = questDragon.getView();

        assertThat(result).isEqualTo("/WEB-INF/quest-dragon.jsp");
    }
}

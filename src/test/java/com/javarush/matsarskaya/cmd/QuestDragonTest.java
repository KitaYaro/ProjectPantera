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

import static com.javarush.matsarskaya.config.ApplicationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for QuestDragon")
class QuestDragonTest {
    public static final String TEST_USER = "testuser";
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

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
    }

    @Test
    @DisplayName("The beginning of the quest")
    void testDoPostStartQuest() {
        when(request.getParameter(PARAM_QUEST)).thenReturn(QUEST_NAME);
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn(null);
        when(request.getParameter(PARAM_CHOICE)).thenReturn(null);
        when(request.getParameter(PARAM_PLAYER_NAME)).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(session).setAttribute(SESSION_ATTR_STAGE, 0);
        verify(session).setAttribute(SESSION_ATTR_TRUST, QUEST_INITIAL_TRUST);
        verify(session).setAttribute(SESSION_ATTR_QUEST_FINISHED, false);
    }

    @Test
    @DisplayName("Transition to stage 1")
    void testDoPostStage0() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("0");
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(session).setAttribute(SESSION_ATTR_STAGE, 1);
    }

    @Test
    @DisplayName("Entering the player's name")
    void testDoPostStage1WithName() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("1");
        when(request.getParameter(PARAM_PLAYER_NAME)).thenReturn("PlayerName");
        when(request.getParameter(PARAM_CHOICE)).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(session).setAttribute(SESSION_ATTR_PLAYER_NAME, "PlayerName");
        verify(session).setAttribute(SESSION_ATTR_STAGE, 2);
    }

    @Test
    @DisplayName("Moving to the next stage with a choice")
    void testDoPostNextStage() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("2");
        when(request.getParameter(PARAM_CHOICE)).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SESSION_USERNAME)).thenReturn(TEST_USER);
        when(session.getAttribute(SESSION_ATTR_QUEST_FINISHED)).thenReturn(false);
        when(session.getAttribute(SESSION_ATTR_TRUST)).thenReturn(QUEST_INITIAL_TRUST);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(session).setAttribute(SESSION_ATTR_TRUST, 60);
        verify(session, atLeast(1)).setAttribute(SESSION_ATTR_STAGE, 3);
    }

    @Test
    @DisplayName("Defeat at a low level of trust")
    void testDoPostLossCondition() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("4");
        when(request.getParameter(PARAM_CHOICE)).thenReturn("-10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SESSION_USERNAME)).thenReturn(TEST_USER);
        when(session.getAttribute(SESSION_ATTR_QUEST_FINISHED)).thenReturn(false);
        when(session.getAttribute(SESSION_ATTR_TRUST)).thenReturn(45);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(statisticService).registerLoss(TEST_USER);
        verify(session).setAttribute(SESSION_ATTR_QUEST_FINISHED, true);
    }

    @Test
    @DisplayName("Winning with a high level of trust")
    void testDoPostWinCondition() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("9");
        when(request.getParameter(PARAM_CHOICE)).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SESSION_USERNAME)).thenReturn(TEST_USER);
        when(session.getAttribute(SESSION_ATTR_QUEST_FINISHED)).thenReturn(false);
        when(session.getAttribute(SESSION_ATTR_TRUST)).thenReturn(60);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(session).setAttribute(SESSION_ATTR_TRUST, QUEST_WIN_THRESHOLD);
        verify(session, atLeast(1)).setAttribute(SESSION_ATTR_STAGE, 10);
    }

    @Test
    @DisplayName("Winning the final stage with a high level of trust")
    void testDoPostFinalStageWin() {
        when(request.getParameter(SESSION_ATTR_STAGE)).thenReturn("10");
        when(request.getParameter(PARAM_CHOICE)).thenReturn("10");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SESSION_USERNAME)).thenReturn(TEST_USER);
        when(session.getAttribute(SESSION_ATTR_QUEST_FINISHED)).thenReturn(false);
        when(session.getAttribute(SESSION_ATTR_TRUST)).thenReturn(QUEST_WIN_THRESHOLD);

        String result = questDragon.doPost(request);

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
        verify(statisticService).registerWin(TEST_USER);
        verify(session).setAttribute(SESSION_ATTR_QUEST_FINISHED, true);
        verify(session, atLeast(1)).setAttribute(SESSION_ATTR_STAGE, 11);
    }

    @Test
    @DisplayName("Getting the path to the view")
    void testGetView() {
        String result = questDragon.getView();

        assertThat(result).isEqualTo(VIEW_QUEST_DRAGON);
    }
}

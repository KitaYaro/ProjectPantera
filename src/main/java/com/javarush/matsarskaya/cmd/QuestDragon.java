package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.IStatisticService;
import com.javarush.matsarskaya.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.javarush.matsarskaya.config.ApplicationConstants.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestDragon implements Command{
    private final IStatisticService statisticService;

    private static final Logger logger =  LoggerFactory.getLogger(QuestDragon.class);

    public QuestDragon(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public String getView() {
        return VIEW_QUEST_DRAGON;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String stageParam = request.getParameter(PARAM_STAGE);
        String choice = request.getParameter(PARAM_CHOICE);
        String playerNameInput = request.getParameter(PARAM_PLAYER_NAME);
        String quest = request.getParameter(PARAM_QUEST);

        var session = request.getSession();

        if (quest != null && quest.equals(QUEST_NAME)) {
            session.setAttribute(SESSION_ATTR_STAGE, 0);
            session.setAttribute(SESSION_ATTR_TRUST, QUEST_INITIAL_TRUST);
            session.setAttribute(SESSION_ATTR_QUEST_FINISHED, false);

            Optional.ofNullable((String) session.getAttribute(SESSION_USERNAME))
                    .ifPresent(statisticService::registerAttempt);
            return getView();
        } else if (stageParam != null) {
            try {
                int currentStage = Integer.parseInt(stageParam);

                if (currentStage == 0) {
                    session.setAttribute(SESSION_ATTR_STAGE, 1);
                } else if (currentStage == 1
                           && playerNameInput != null && !playerNameInput.isEmpty()) {

                    session.setAttribute(SESSION_ATTR_PLAYER_NAME, playerNameInput);
                    session.setAttribute(SESSION_ATTR_STAGE, 2);

                } else if (currentStage >= 2 && currentStage <= 10) {
                    // Обработка выбора дракона или действий
                    if (choice != null) {
                        String username = (String) session.getAttribute(SESSION_USERNAME);
                        Boolean finished = Optional.ofNullable((Boolean) session.getAttribute(SESSION_ATTR_QUEST_FINISHED))
                                .orElse(false);

                        Integer trust = Optional.ofNullable((Integer) session.getAttribute(SESSION_ATTR_TRUST))
                                .orElse(QUEST_DEFAULT_TRUST);

                        int trustChange;
                        try {
                            trustChange = Integer.parseInt(choice);
                        } catch (NumberFormatException e) {
                            // Если choice не является числом, предполагаем что это выбор дракона на этапе 2
                            trustChange = 0;
                        }

                        trust += trustChange;
                        trust = Math.max(0, Math.min(100, trust));

                        session.setAttribute(SESSION_ATTR_TRUST, trust);

                        logger.info("STAGE DEBUG: currentStage={}, trust={}", currentStage, trust);
                        boolean isLossCondition = false;
                        if (currentStage >= 4 && currentStage <= 7 && trust < QUEST_LOSS_THRESHOLD_EARLY) {
                            isLossCondition = true;
                        } else if (currentStage >= 8 && currentStage <= 11 && trust < QUEST_LOSS_THRESHOLD_LATE) {
                            isLossCondition = true;
                        }

                        if (!finished && isLossCondition) {
                            logger.info("CHECK: finished={}, isLossCondition={}, username={}, trust={}, stage={}",
                                    finished, isLossCondition, username, trust, currentStage);

                            Optional.ofNullable(username).ifPresent(statisticService::registerLoss);
                            session.setAttribute(SESSION_ATTR_QUEST_FINISHED, true);
                            return getView();
                        }
                    }

                    int nextStage = currentStage + 1;
                    session.setAttribute(SESSION_ATTR_STAGE, nextStage);

                    Integer trust = (Integer) session.getAttribute(SESSION_ATTR_TRUST);
                    String username = (String) session.getAttribute(SESSION_USERNAME);

                    if (!Optional.ofNullable((Boolean) session.getAttribute(SESSION_ATTR_QUEST_FINISHED)).orElse(false) && nextStage == 11) {
                        session.setAttribute(SESSION_ATTR_QUEST_FINISHED, true);
                        if (trust != null && trust >= QUEST_WIN_THRESHOLD) {
                            Optional.ofNullable(username).ifPresent(statisticService::registerWin);
                        } else {
                            Optional.ofNullable(username).ifPresent(statisticService::registerLoss);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // Если stageParam не является числом, перенаправляем на домашнюю страницу
                return VIEW_HOME;
            }
        } else {
            return VIEW_HOME;
        }
        return getView();
    }
}

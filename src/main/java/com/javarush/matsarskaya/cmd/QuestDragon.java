package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class QuestDragon implements Command{
    private final StatisticService statisticService;

    public QuestDragon(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public String getView() {
        return "/WEB-INF/quest-dragon.jsp";
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String stageParam = request.getParameter("stage");
        String choice = request.getParameter("choice");
        String playerNameInput = request.getParameter("playerNameInput");
        String quest = request.getParameter("quest");

        var session = request.getSession();

        if (quest != null && quest.equals("the way of the dragon rider")) {
            session.setAttribute("stage", 0);
            session.setAttribute("trust", 50);
            session.setAttribute("questFinished", false);

            Optional.ofNullable((String) session.getAttribute("username"))
                    .ifPresent(statisticService::registerAttempt);
            return getView();
        } else if (stageParam != null && Integer.parseInt(stageParam) == 0) {
            session.setAttribute("stage", 1);
        } else if (stageParam != null && Integer.parseInt(stageParam) == 1
                   && playerNameInput != null && !playerNameInput.isEmpty()) {

            session.setAttribute("playerName", playerNameInput);
            session.setAttribute("stage", 2);

        } else if (stageParam != null && choice != null) {

            int currentStage = Integer.parseInt(stageParam);

            String username = (String) session.getAttribute("username");
            Boolean finished = Optional.ofNullable((Boolean) session.getAttribute("questFinished"))
                    .orElse(false);

            if (currentStage >= 2 && currentStage <= 10) {
                Integer trust = Optional.ofNullable((Integer) session.getAttribute("trust"))
                        .orElse(50);

                int trustChange = Integer.parseInt(choice);
                trust += trustChange;
                trust = Math.max(0, Math.min(100, trust));

                session.setAttribute("trust", trust);

                boolean isLossCondition = false;
                if (currentStage >= 4 && currentStage <= 7 && trust < 50) {
                    isLossCondition = true;
                } else if (currentStage >= 8 && currentStage <= 11 && trust < 70) {
                    isLossCondition = true;
                }

                if (!finished && isLossCondition) {
                    Optional.ofNullable(username).ifPresent(statisticService::registerLoss);
                    session.setAttribute("questFinished", true);
                    return getView();
                }
            }

            int nextStage = currentStage + 1;
            session.setAttribute("stage", nextStage);

            Integer trust = (Integer) session.getAttribute("trust");

            if (!finished && nextStage == 11) {
                session.setAttribute("questFinished", true);
                if (trust != null && trust >= 70) {
                    Optional.ofNullable(username).ifPresent(statisticService::registerWin);
                } else {
                    Optional.ofNullable(username).ifPresent(statisticService::registerLoss);
                }
            }

        } else {
            return "/WEB-INF/home-page.jsp";
        }
        return getView();
    }
}
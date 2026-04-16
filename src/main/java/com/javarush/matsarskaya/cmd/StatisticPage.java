package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.service.IStatisticService;
import com.javarush.matsarskaya.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.javarush.matsarskaya.config.ApplicationConstants.VIEW_STATISTIC;
import static com.javarush.matsarskaya.config.ApplicationConstants.SESSION_USERNAME;

    public class StatisticPage implements Command{
    private final IStatisticService statisticService;

    public StatisticPage(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Optional<String> username = Optional.ofNullable((String) request.getSession().getAttribute(SESSION_USERNAME));

        username.ifPresent(un -> {
            Optional<Statistic> statistic = statisticService.getStatistic(un);
            statistic.ifPresent(s -> request.setAttribute("statistic", s));
        });

        return getView();
    }

    @Override
    public String getView() {
        return VIEW_STATISTIC;
    }
}

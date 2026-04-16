package com.javarush.matsarskaya.mapper;

import com.javarush.matsarskaya.dto.StatisticDTO;
import com.javarush.matsarskaya.entity.Statistic;

public class StatisticMapper {

    public static StatisticDTO toDTO(Statistic statistic){
        if (statistic == null) {
            return null;
        }
        StatisticDTO dto = new StatisticDTO();
        dto.setId(statistic.getId());
        dto.setUser(UserMapper.toDTO(statistic.getUser()));
        dto.setAttempts(statistic.getAttempts());
        dto.setWins(statistic.getWins());
        dto.setLosses(statistic.getLosses());

        if (statistic.getUser() != null) {
            dto.setUser(UserMapper.toDTO(statistic.getUser()));
        }

        return dto;
    }

    public static Statistic toEntity(StatisticDTO dto){
        if (dto == null) {
            return null;
        }
        Statistic statistic = new Statistic();
        statistic.setId(dto.getId());
        statistic.setUser(UserMapper.toEntity(dto.getUser()));
        statistic.setAttempts(dto.getAttempts() != null ? dto.getAttempts() : 0);
        statistic.setWins(dto.getWins() != null ? dto.getWins() : 0);
        statistic.setLosses(dto.getLosses() != null ? dto.getLosses() : 0);

        if (dto.getUser() != null) {
            statistic.setUser(UserMapper.toEntity(dto.getUser()));
        }

        return statistic;
    }
}

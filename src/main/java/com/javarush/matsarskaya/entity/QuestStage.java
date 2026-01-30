package com.javarush.matsarskaya.entity;

import java.util.Arrays;

public enum QuestStage {
    START(0),
    NAME_INPUT(1),
    STAGE_2(2), STAGE_3(3), STAGE_4(4), STAGE_5(5),
    STAGE_6(6), STAGE_7(7), STAGE_8(8), STAGE_9(9),
    PRE_FINAL(10),
    FINAL(11);

    private final int stageNumber;

    QuestStage(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public static QuestStage fromNumber(int number) {
        return Arrays.stream(values())
                .filter(stage -> stage.stageNumber == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid stage number: " + number));
    }

    public QuestStage next() {
        return fromNumber(stageNumber + 1);
    }

    public boolean isLossStage(int trust) {
        if (stageNumber >= 3 && stageNumber <= 6) {
            return trust < 50;
        } else if (stageNumber >= 7 && stageNumber <= 10) {
            return trust < 70;
        }
        return false;
    }
}

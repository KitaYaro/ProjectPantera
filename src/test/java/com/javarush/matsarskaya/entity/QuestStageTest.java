package com.javarush.matsarskaya.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the enum QuestStage")
class QuestStageTest {

    @Test
    @DisplayName("Getting the stage number")
    void testGetStageNumber() {
        assertThat(QuestStage.START.getStageNumber()).isEqualTo(0);
        assertThat(QuestStage.NAME_INPUT.getStageNumber()).isEqualTo(1);
        assertThat(QuestStage.STAGE_2.getStageNumber()).isEqualTo(2);
        assertThat(QuestStage.FINAL.getStageNumber()).isEqualTo(11);
    }

    @Test
    @DisplayName("Getting a stage by number")
    void testFromNumber() {
        assertThat(QuestStage.fromNumber(0)).isEqualTo(QuestStage.START);
        assertThat(QuestStage.fromNumber(1)).isEqualTo(QuestStage.NAME_INPUT);
        assertThat(QuestStage.fromNumber(5)).isEqualTo(QuestStage.STAGE_5);
        assertThat(QuestStage.fromNumber(11)).isEqualTo(QuestStage.FINAL);
    }

    @Test
    @DisplayName("Throwing an exception for an incorrect stage number")
    void testFromNumberWithInvalidNumber() {
        assertThatThrownBy(() -> QuestStage.fromNumber(12))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid stage number: 12");
        
        assertThatThrownBy(() -> QuestStage.fromNumber(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid stage number: -1");
    }

    @Test
    @DisplayName("Moving on to the next stage")
    void testNext() {
        assertThat(QuestStage.START.next()).isEqualTo(QuestStage.NAME_INPUT);
        assertThat(QuestStage.NAME_INPUT.next()).isEqualTo(QuestStage.STAGE_2);
        assertThat(QuestStage.PRE_FINAL.next()).isEqualTo(QuestStage.FINAL);
    }

    @ParameterizedTest
    @CsvSource({
        "3, 49, true",
        "3, 50, false",
        "4, 30, true",
        "4, 50, false",
        "5, 45, true",
        "5, 50, false",
        "6, 49, true",
        "6, 50, false"
    })
    @DisplayName("Checking the defeat condition for stages 3-6")
    void testIsLossStageForStages3To6(int stageNumber, int trust, boolean expected) {
        QuestStage stage = QuestStage.fromNumber(stageNumber);
        assertThat(stage.isLossStage(trust)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "7, 69, true",
        "7, 70, false",
        "8, 50, true",
        "8, 70, false",
        "9, 60, true",
        "9, 70, false",
        "10, 69, true",
        "10, 70, false"
    })
    @DisplayName("Checking the damage condition for stages 7-10")
    void testIsLossStageForStages7To10(int stageNumber, int trust, boolean expected) {
        QuestStage stage = QuestStage.fromNumber(stageNumber);
        assertThat(stage.isLossStage(trust)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 11})
    @DisplayName("Stages 0, 1, 2, and 11 do not have a defeat condition.")
    void testIsLossStageForNonLossStages(int stageNumber) {
        QuestStage stage = QuestStage.fromNumber(stageNumber);
        assertThat(stage.isLossStage(0)).isFalse();
        assertThat(stage.isLossStage(100)).isFalse();
    }
}

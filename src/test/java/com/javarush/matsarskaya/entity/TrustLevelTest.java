package com.javarush.matsarskaya.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for enum TrustLevel")
class TrustLevelTest {

    @Test
    @DisplayName("Getting the confidence level by value")
    void testFromValue() {
        assertThat(TrustLevel.fromValue(0)).isEqualTo(TrustLevel.CRITICAL);
        assertThat(TrustLevel.fromValue(25)).isEqualTo(TrustLevel.CRITICAL);
        assertThat(TrustLevel.fromValue(49)).isEqualTo(TrustLevel.CRITICAL);
        
        assertThat(TrustLevel.fromValue(50)).isEqualTo(TrustLevel.LOW);
        assertThat(TrustLevel.fromValue(60)).isEqualTo(TrustLevel.LOW);
        assertThat(TrustLevel.fromValue(69)).isEqualTo(TrustLevel.LOW);
        
        assertThat(TrustLevel.fromValue(70)).isEqualTo(TrustLevel.HIGH);
        assertThat(TrustLevel.fromValue(85)).isEqualTo(TrustLevel.HIGH);
        assertThat(TrustLevel.fromValue(100)).isEqualTo(TrustLevel.HIGH);
    }

    @ParameterizedTest
    @CsvSource({
        "CRITICAL, 3, false",
        "CRITICAL, 4, false",
        "CRITICAL, 5, false",
        "CRITICAL, 6, false",
        "LOW, 3, true",
        "LOW, 4, true",
        "LOW, 5, true",
        "LOW, 6, true",
        "HIGH, 3, true",
        "HIGH, 4, true",
        "HIGH, 5, true",
        "HIGH, 6, true"
    })
    @DisplayName("Verification of the sufficiency of the trust level for stages 3-6")
    void testIsSufficientForStageForStages3To6(TrustLevel level, int stageNumber, boolean expected) {
        assertThat(level.isSufficientForStage(stageNumber)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "CRITICAL, 7, false",
        "CRITICAL, 8, false",
        "CRITICAL, 9, false",
        "CRITICAL, 10, false",
        "LOW, 7, false",
        "LOW, 8, false",
        "LOW, 9, false",
        "LOW, 10, false",
        "HIGH, 7, true",
        "HIGH, 8, true",
        "HIGH, 9, true",
        "HIGH, 10, true"
    })
    @DisplayName("Verification of the sufficiency of the trust level for stages 7-10")
    void testIsSufficientForStageForStages7To10(TrustLevel level, int stageNumber, boolean expected) {
        assertThat(level.isSufficientForStage(stageNumber)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 11})
    @DisplayName("Verification of the sufficiency of the trust level for stages 0, 1, 2 and 11")
    void testIsSufficientForStageForNonCriticalStages(int stageNumber) {
        assertThat(TrustLevel.CRITICAL.isSufficientForStage(stageNumber)).isTrue();
        assertThat(TrustLevel.LOW.isSufficientForStage(stageNumber)).isTrue();
        assertThat(TrustLevel.HIGH.isSufficientForStage(stageNumber)).isTrue();
    }
}

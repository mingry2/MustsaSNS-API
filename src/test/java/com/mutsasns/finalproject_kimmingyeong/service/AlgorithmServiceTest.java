package com.mutsasns.finalproject_kimmingyeong.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmServiceTest {

    AlgorithmService algorithmService = new AlgorithmService();

    @Test
    @DisplayName("자릿수의 합이 잘 구해지는지")
    void sumOfDigit(){
        assertEquals(28, algorithmService.sumOfDigit(7777));
        assertEquals(21, algorithmService.sumOfDigit(687));
        assertEquals(18, algorithmService.sumOfDigit(99));
    }

}
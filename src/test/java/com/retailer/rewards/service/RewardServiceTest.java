package com.retailer.rewards.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.retailer.rewards.exception.InvalidTransactionException;

class RewardServiceTest {

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        rewardService = new RewardService();
    }

    @Test
    void testPointsOver100() {
        assertEquals(90, rewardService.calculatePointsForAmount(new BigDecimal("120.00")));
    }

    @Test
    void testPointsBetween50And100() {
        assertEquals(25, rewardService.calculatePointsForAmount(new BigDecimal("75.00")));
    }

    @Test
    void testNegativeAmount() {
        assertThrows(InvalidTransactionException.class, () ->
                rewardService.calculatePointsForAmount(new BigDecimal("-10.00")));
    }
}
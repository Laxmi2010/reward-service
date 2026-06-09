package com.retailer.rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.retailer.rewards.exception.InvalidTransactionException;
import com.retailer.rewards.model.CustomerRewardSummary;
import com.retailer.rewards.model.Transaction;
import com.retailer.rewards.service.RewardService;

/**
 * Unit tests for RewardService, covering point calculation, aggregation, and
 * negative scenarios.
 */
class RewardServiceTest {

	private RewardService rewardService;

	@BeforeEach
	void setUp() {
		rewardService = new RewardService();
	}

	@Test
	void testCalculatePoints_below50() {
		assertEquals(0, rewardService.calculatePointsForAmount(new BigDecimal("49.99")));
		assertEquals(0, rewardService.calculatePointsForAmount(new BigDecimal("50.00")));
	}

	@Test
	void testCalculatePoints_between50And100() {
		assertEquals(25, rewardService.calculatePointsForAmount(new BigDecimal("75.00")));
		assertEquals(50, rewardService.calculatePointsForAmount(new BigDecimal("100.00")));
	}

	@Test
	void testCalculatePoints_over100() {
		assertEquals(90, rewardService.calculatePointsForAmount(new BigDecimal("120.00")));
		assertEquals(250, rewardService.calculatePointsForAmount(new BigDecimal("200.00")));
	}

	@Test
	void testCalculatePoints_negativeAmountThrowsException() {
		assertThrows(InvalidTransactionException.class, () -> {
			rewardService.calculatePointsForAmount(new BigDecimal("-10.00"));
		});
	}

	@Test
	void testCalculateRewards_aggregation() {
		LocalDate date1 = LocalDate.of(2023, 1, 15);
		LocalDate date2 = LocalDate.of(2023, 2, 20);

		List<Transaction> transactions = Arrays.asList(new Transaction(1L, new BigDecimal("120.00"), date1), // 90 pts
				new Transaction(1L, new BigDecimal("120.00"), date1), // 90 pts (Jan total = 180)
				new Transaction(1L, new BigDecimal("80.00"), date2), // 30 pts (Feb total = 30)
				new Transaction(2L, new BigDecimal("200.00"), date1) // 250 pts (Jan total = 250)
		);

		List<CustomerRewardSummary> summaries = rewardService.calculateRewards(transactions);

		assertEquals(2, summaries.size());

		// Validate Customer 1
		CustomerRewardSummary c1 = summaries.stream().filter(s -> s.getCustomerId() == 1L).findFirst().get();
		assertEquals(210, c1.getTotalPoints());
		assertEquals(180, c1.getMonthlyPoints().get("January-2023"));
		assertEquals(30, c1.getMonthlyPoints().get("February-2023"));

		// Validate Customer 2
		CustomerRewardSummary c2 = summaries.stream().filter(s -> s.getCustomerId() == 2L).findFirst().get();
		assertEquals(250, c2.getTotalPoints());
		assertEquals(250, c2.getMonthlyPoints().get("January-2023"));
	}
}
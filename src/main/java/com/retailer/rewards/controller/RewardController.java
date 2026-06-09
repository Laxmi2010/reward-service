package com.retailer.rewards.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewards.model.CustomerRewardSummary;
import com.retailer.rewards.model.Transaction;
import com.retailer.rewards.service.RewardService;

/**
 * REST controller for exposing reward calculation endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

	private final RewardService rewardService;

	@Autowired
	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	/**
	 * Endpoint to calculate rewards based on a provided JSON payload of
	 * transactions. * @param transactions List of transactions
	 * 
	 * @return Calculated summary per customer
	 */
	@PostMapping("/calculate")
	public ResponseEntity<List<CustomerRewardSummary>> calculateRewards(@RequestBody List<Transaction> transactions) {
		List<CustomerRewardSummary> summary = rewardService.calculateRewards(transactions);
		return ResponseEntity.ok(summary);
	}

	/**
	 * Endpoint providing a mocked data set to best demonstrate the solution
	 * quickly. * @return Calculated summary based on internal mock data
	 */
	@GetMapping("/getRewards")
	public ResponseEntity<List<CustomerRewardSummary>> getDemoRewards() {
		LocalDate now = LocalDate.now();
		List<Transaction> demoTransactions = Arrays.asList(
		        // Customer 1 Data
		        new Transaction(1L, new BigDecimal("120.00"), now.minusMonths(2)),
		        new Transaction(1L, new BigDecimal("80.00"), now.minusMonths(1)),
		        new Transaction(1L, new BigDecimal("40.00"), now),

		        // Customer 2 Data
		        new Transaction(2L, new BigDecimal("200.00"), now.minusMonths(2)),
		        new Transaction(2L, new BigDecimal("150.00"), now.minusMonths(2)),
		        new Transaction(2L, new BigDecimal("90.00"), now)
		);

		return ResponseEntity.ok(rewardService.calculateRewards(demoTransactions));
	}
}
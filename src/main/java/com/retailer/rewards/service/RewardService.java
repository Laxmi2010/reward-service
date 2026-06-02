package com.retailer.rewards.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.retailer.rewards.exception.InvalidTransactionException;
import com.retailer.rewards.model.CustomerRewardSummary;
import com.retailer.rewards.model.Transaction;

/**
 * Service class responsible for business logic regarding reward point
 * calculations.
 */
@Service
public class RewardService {

	/**
	 * Calculates the reward points for a given purchase amount. Rules: 2 points for
	 * every dollar over $100, 1 point for every dollar between $50 and $100.
	 *
	 * @param amount The purchase amount
	 * @return The calculated reward points
	 */
	public int calculatePointsForAmount(double amount) {
		if (amount < 0) {
			throw new InvalidTransactionException("Transaction amount cannot be negative.");
		}

		int points = 0;
		int intAmount = (int) Math.floor(amount);

		if (intAmount > 100) {
			points += (intAmount - 100) * 2;
			points += 50; // Max points from the $50-$100 bracket
		} else if (intAmount > 50) {
			points += (intAmount - 50);
		}

		return points;
	}

	/**
	 * Processes a list of transactions and calculates monthly and total points per
	 * customer. Note: Months are dynamic (YearMonth) to avoid hardcoding.
	 *
	 * @param transactions List of all transactions
	 * @return List of CustomerRewardSummary
	 */
	public List<CustomerRewardSummary> calculateRewards(List<Transaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return Collections.emptyList();
		}

		// Group by Customer ID
		Map<Long, List<Transaction>> transactionsByCustomer = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		List<CustomerRewardSummary> summaries = new ArrayList<>();

		for (Map.Entry<Long, List<Transaction>> entry : transactionsByCustomer.entrySet()) {
			Long customerId = entry.getKey();
			List<Transaction> customerTransactions = entry.getValue();

			Map<String, Integer> monthlyPoints = new LinkedHashMap<>();
			int totalPoints = 0;

			for (Transaction t : customerTransactions) {
				int points = calculatePointsForAmount(t.getAmount());
				// Using YearMonth ensures we don't hardcode months and handles multi-year data
//                String monthKey = YearMonth.from(t.getTransactionDate()).toString(); 
				String monthKey = t.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "-"
						+ t.getTransactionDate().getYear();

				monthlyPoints.put(monthKey, monthlyPoints.getOrDefault(monthKey, 0) + points);
				totalPoints += points;
			}

			summaries.add(new CustomerRewardSummary(customerId, monthlyPoints, totalPoints));
		}

		return summaries;
	}
}
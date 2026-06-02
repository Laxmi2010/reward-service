package com.retailer.rewards.model;

import java.util.Map;

/**
 * Data Transfer Object containing the calculated rewards for a specific customer.
 */
public class CustomerRewardSummary {
    private Long customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

    public CustomerRewardSummary(Long customerId, Map<String, Integer> monthlyPoints, int totalPoints) {
        this.customerId = customerId;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Map<String, Integer> getMonthlyPoints() { return monthlyPoints; }
    public void setMonthlyPoints(Map<String, Integer> monthlyPoints) { this.monthlyPoints = monthlyPoints; }
    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
}
package com.retailer.rewards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testValidRequest() throws Exception {

		String json = """
            [
              {"customerId": 1, "amount": 120.0, "transactionDate": "2023-01-15"}
            ]
            """;

		mockMvc.perform(post("/api/rewards/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].totalPoints").value(90));
	}
}

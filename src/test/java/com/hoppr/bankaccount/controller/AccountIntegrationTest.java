package com.hoppr.bankaccount.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AccountIntegrationTest {
  @Autowired private MockMvc mockMvc;

  @Test
  void getExistingAccountShouldReturnAccount() throws Exception {
    mockMvc
        .perform(get("/account/1"))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.amount").value(100.5))
        .andExpect(jsonPath("$.closed").value(false));
  }

  @Test
  void getNonExistingAccountShouldThrowGet404Error() throws Exception {
    mockMvc
        .perform(get("/account/9999999"))
        .andExpect(status().is(404))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Account not found"));
  }
}

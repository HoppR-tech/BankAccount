package com.hoppr.bankaccount;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
  @Autowired private MockMvc mockMvc;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.flyway.url", postgres::getJdbcUrl);
    registry.add("spring.flyway.user", postgres::getUsername);
    registry.add("spring.flyway.password", postgres::getPassword);
  }

  @Test
  void getExistingAccountShouldReturnAccount() throws Exception {
    mockMvc
        .perform(get("/account/7"))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(7))
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

  @Test
  void creditAccount() throws Exception {

    var payload =
        """
          {
            "amount": 100
          }
        """;

    mockMvc
        .perform(
            patch("/account/1/credit").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.amount").value(200.5))
        .andExpect(jsonPath("$.closed").value(false));
  }

  @Test
  void creditAccountWithNegativeAmount() throws Exception {

    var payload =
        """
              {
                "amount": -100
              }
            """;

    mockMvc
        .perform(
            patch("/account/1/credit").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Amount must be positive"));
  }

  @Test
  void creditClosedAccount() throws Exception {

    var payload =
        """
              {
                "amount": 100
              }
            """;

    mockMvc
        .perform(
            patch("/account/6/credit").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Cannot perform operation on closed account"));
  }

  @Test
  void debitAccount() throws Exception {

    var payload =
        """
              {
                "amount": 100
              }
            """;

    mockMvc
        .perform(patch("/account/2/debit").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.amount").value(-110.2))
        .andExpect(jsonPath("$.closed").value(false));
  }

  @Test
  void withDrawPositiveAccount() throws Exception {

    var payload =
        """
                  {
                    "amount": 100
                  }
                """;

    mockMvc
        .perform(
            patch("/account/3/withdraw").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.amount").value(100.5))
        .andExpect(jsonPath("$.closed").value(false));
  }

  @Test
  void withdrawNegativeAccount() throws Exception {

    var payload =
        """
         {
            "amount": 100
         }
        """;

    mockMvc
        .perform(
            patch("/account/4/withdraw").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Not enough money on the account"));
  }

  @Test
  void closeOpenAccount() throws Exception {
    mockMvc.perform(patch("/account/5/close")).andExpect(status().is(200));
  }

  @Test
  void closeClosedAccount() throws Exception {
    mockMvc
        .perform(patch("/account/8/close"))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Account already closed"));
  }

  @Test
  void reopenClosedAccount() throws Exception {
    mockMvc.perform(patch("/account/6/reopen")).andExpect(status().is(200));
  }

  @Test
  void reopenOpenAccount() throws Exception {
    mockMvc
        .perform(patch("/account/1/reopen"))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Account is not closed"));
  }
}

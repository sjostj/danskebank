package dk.jost.danskebank;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DanskebankApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void test404IfInvalidAccountId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/accounts/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void testCreateAccount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test-account"))
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/accounts/" + result.getResponse().getContentAsString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("test-account"))
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    @Order(3)
    public void testNoOverdraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-100.00"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(4)
    public void testValidTransactions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("100"))
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/accounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(100.0));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-45.50"))
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(54.5));
    }

    @Test
    @Order(5)
    public void testTransactionslistOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].amount").value(-45.5))
                .andExpect(jsonPath("$.[1].amount").value(100));
    }

    @Test
    @Order(6)
    public void testTransactionlistMaxSize() throws Exception {
        for (int i=0; i<10; i++) {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/v1/accounts/1/transactions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("100"))
                    .andExpect(status().is2xxSuccessful());
        }
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(10)));
    }

}

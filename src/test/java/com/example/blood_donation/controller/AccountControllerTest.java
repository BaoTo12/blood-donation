package com.example.blood_donation.controller;
//
//import com.example.blood_donation.dto.request.account.AccountCreationRequest;
//import com.example.blood_donation.dto.response.account.AccountResponse;
//import com.example.blood_donation.service.AccountService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AccountControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    // Updated annotation from @MockBean to @MockitoBean
//    @MockitoBean
//    private AccountService accountService; // Fixed variable name to match service type
//
//    private AccountCreationRequest request;
//    private AccountResponse accountResponse; // Fixed variable name for consistency
//
//    @BeforeEach
//    void initData(){
//
//        // Build the account creation request with proper field names
//        request = AccountCreationRequest.builder()
//                .name("john")
//                .password("12345678")
//                .build();
//
//        // Build the expected response object
//        accountResponse = AccountResponse.builder()
//                .build();
//    }
//
//    @Test
//    void createAccount_validRequest_success() throws Exception {
//        // GIVEN - Set up test data and mock behavior
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // Handle LocalDate serialization
//        String content = objectMapper.writeValueAsString(request);
//
//        // Mock the service method to return our expected response
//        Mockito.when(accountService.createAccount(ArgumentMatchers.any(AccountCreationRequest.class)))
//                .thenReturn(accountResponse);
//
//        // WHEN & THEN - Perform the request and verify the response
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/accounts") // Updated endpoint to match Account entity
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code")
//                        .value(1000))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
//                        .value("cf0600f538b3"))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.username")
//                        .value("john"))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName")
//                        .value("John"));
//    }
//
//    @Test
//    void createAccount_usernameInvalid_fail() throws Exception {
//        // GIVEN - Set up invalid request data
//        request.setUsername("joh"); // Username too short (less than 4 characters)
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String content = objectMapper.writeValueAsString(request);
//
//        // WHEN & THEN - Perform request and expect validation failure
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/accounts") // Updated endpoint
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("code")
//                        .value(1003))
//                .andExpect(MockMvcResultMatchers.jsonPath("message")
//                        .value("Username must be at least 4 characters"));
//    }
//
//    @Test
//    void createAccount_passwordTooShort_fail() throws Exception {
//        // GIVEN - Set up request with invalid password
//        request.setPassword("123"); // Password too short
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String content = objectMapper.writeValueAsString(request);
//
//        // WHEN & THEN - Perform request and expect validation failure
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/accounts")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpected(MockMvcResultMatchers.status().isBadRequest())
//                .andExpected(MockMvcResultMatchers.jsonPath("code")
//                        .exists()) // Verify error code exists
//                .andExpected(MockMvcResultMatchers.jsonPath("message")
//                        .value("Password must be at least 8 characters"));
//    }
//}
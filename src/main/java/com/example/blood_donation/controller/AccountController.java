package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.AccountCreationRequest;
import com.example.blood_donation.dto.request.AccountUpdateRequest;
import com.example.blood_donation.dto.response.AccountResponse;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @GetMapping
    public ApiResponse<List<Account>> getAccounts() {
        return ApiResponse.<List<Account>>builder().result(accountService.getAllAccounts()).build();
    }

    @PostMapping
    public ApiResponse<Long> createAccount(@Valid @RequestBody AccountCreationRequest request){
        var result = accountService.createAccount(request);
        return ApiResponse.<Long>builder().result(result).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id){
        return ApiResponse.<AccountResponse>builder().result(accountService.getAccountById(id)).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest request){
        accountService.updateAccount(request, id);
        return ApiResponse.<String>builder().result("Updated account successfully").build();
    }
}

package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.account.AccountCreationRequest;
import com.example.blood_donation.dto.request.account.AccountUpdateRequest;
import com.example.blood_donation.dto.response.account.AccountResponse;
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
@RequestMapping("${api.base-path}/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @GetMapping
    public ApiResponse<List<Account>> getAccounts() {
        return ApiResponse.<List<Account>>builder().result(accountService.getAllAccounts()).build();
    }


    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id) {
        return ApiResponse.<AccountResponse>builder().result(accountService.getAccountById(id)).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest request) {
        accountService.updateAccount(request, id);
        return ApiResponse.<String>builder().result("Updated account successfully").build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ApiResponse.<String>builder().result("Delete account successfully").build();
    }
}

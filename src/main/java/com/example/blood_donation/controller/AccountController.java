package com.example.blood_donation.controller;

import com.example.blood_donation.dto.requests.account.AccountUpdateRequest;
import com.example.blood_donation.dto.responses.ApiResponse;
import com.example.blood_donation.dto.responses.account.AccountResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.base-path}/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Account>> getAccounts() {
        return ApiResponse.<List<Account>>builder().result(accountService.getAllAccounts()).build();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id) {
        return ApiResponse.<AccountResponse>builder().result(accountService.getAccountById(id)).build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ApiResponse<String> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest request) {
        accountService.updateAccount(request, id);
        return ApiResponse.<String>builder().result("Updated account successfully").build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ApiResponse<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ApiResponse.<String>builder().result("Delete account successfully").build();
    }
}

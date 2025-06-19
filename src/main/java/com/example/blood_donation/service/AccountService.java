package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.account.AccountCreationRequest;
import com.example.blood_donation.dto.request.account.AccountUpdateRequest;
import com.example.blood_donation.dto.response.account.AccountResponse;
import com.example.blood_donation.entity.Account;

import java.util.List;

public interface AccountService {
    Long createAccount(AccountCreationRequest request);

    List<Account> getAllAccounts();

    AccountResponse getAccountById(Long id);

    void updateAccount(AccountUpdateRequest request, Long id);

    void deleteAccount(Long id);
}

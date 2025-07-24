package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.account.AccountCreationRequest;
import com.example.blood_donation.dto.requests.account.AccountUpdateRequest;
import com.example.blood_donation.dto.responses.account.AccountResponse;
import com.example.blood_donation.entity.Account;

import java.util.List;

public interface AccountService {
    Long createAccount(AccountCreationRequest request);

    List<Account> getAllAccounts();

    AccountResponse getAccountById(Long id);

    void updateAccount(AccountUpdateRequest request, Long id);

    void deleteAccount(Long id);
}

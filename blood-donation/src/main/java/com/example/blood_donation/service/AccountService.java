package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.AccountRequest;
import com.example.blood_donation.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Long createAccount(AccountRequest request);

    List<Account> getAllAccounts();
}

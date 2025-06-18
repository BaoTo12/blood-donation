package com.example.blood_donation.service;


import com.example.blood_donation.dto.request.AccountRequest;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.mapper.AccountMapper;
import com.example.blood_donation.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper mapper;

    @Override
    public Long createAccount(AccountRequest request) {
        Account account = mapper.toAccount(request);
        Account savedAccount = accountRepository.save(account);
        System.out.println("AccountRequest: " + request);
        System.out.println("Account: " + account);
        return savedAccount.getId();
    }

    @Override
    public List<Account> getAllAccounts() {
        return List.of();
    }
}

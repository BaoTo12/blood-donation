package com.example.blood_donation.service;


import com.example.blood_donation.dto.request.AccountCreationRequest;
import com.example.blood_donation.dto.request.AccountUpdateRequest;
import com.example.blood_donation.dto.response.AccountResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.AccountMapper;
import com.example.blood_donation.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper mapper;

    @Override
    public Long createAccount(AccountCreationRequest request) {
        Account account = mapper.toAccount(request);
        Account savedAccount = accountRepository.save(account);
        System.out.println("AccountRequest: " + request);
        System.out.println("Account: " + account);
        return savedAccount.getId();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()){
            throw  new ResourceNotFoundException("There is not account with id: " + id);
        }
        return mapper.toAccountResponse(account.get());
    }

    @Override
    public void updateAccount(AccountUpdateRequest request, Long id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is not account with id: " + id));
        mapper.updateFromDto(request, existingAccount);

        accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}

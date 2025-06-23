package com.example.blood_donation.service;


import com.example.blood_donation.dto.request.account.AccountCreationRequest;
import com.example.blood_donation.dto.request.account.AccountUpdateRequest;
import com.example.blood_donation.dto.response.account.AccountResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Role;
import com.example.blood_donation.enumType.PreDefinedRole;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.AccountMapper;
import com.example.blood_donation.repository.AccountRepository;
import com.example.blood_donation.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper mapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    public Long createAccount(AccountCreationRequest request) {
        Account account = mapper.toAccount(request);
        Role role = roleRepository.findByName(PreDefinedRole.MEMBER.name());
        account.getRoles().add(role);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("returnObject.email == authentication.name")
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
        if (!Objects.isNull(request.getPassword())){
            existingAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}

package com.example.blood_donation.mapper;


import com.example.blood_donation.dto.request.AccountCreationRequest;
import com.example.blood_donation.dto.response.AccountResponse;
import com.example.blood_donation.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AccountMapper {
    Account toAccount(AccountCreationRequest request);
    AccountResponse toAccountResponse(Account account);
}

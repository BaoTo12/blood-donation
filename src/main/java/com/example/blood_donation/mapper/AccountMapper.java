package com.example.blood_donation.mapper;


import com.example.blood_donation.dto.requests.account.AccountCreationRequest;
import com.example.blood_donation.dto.requests.account.AccountUpdateRequest;
import com.example.blood_donation.dto.responses.account.AccountResponse;
import com.example.blood_donation.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AccountMapper {
    Account toAccount(AccountCreationRequest request);

    AccountResponse toAccountResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateFromDto(AccountUpdateRequest request, @MappingTarget Account account);
}

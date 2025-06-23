package com.example.blood_donation.mapper;


import com.example.blood_donation.dto.request.account.AccountCreationRequest;
import com.example.blood_donation.dto.request.account.AccountUpdateRequest;
import com.example.blood_donation.dto.response.account.AccountResponse;
import com.example.blood_donation.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AccountMapper {
    Account toAccount(AccountCreationRequest request);

    AccountResponse toAccountResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void updateFromDto(AccountUpdateRequest request, @MappingTarget Account account);
}

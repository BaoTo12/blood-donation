package com.example.blood_donation.mapper;


import com.example.blood_donation.dto.request.AccountRequest;
import com.example.blood_donation.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountRequest request);
}

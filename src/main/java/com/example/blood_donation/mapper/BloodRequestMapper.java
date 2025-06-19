package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.BloodRequest;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BloodRequestMapper {

    @Autowired
    private EntityManager entityManager;

    @Mapping(target = "account", expression = "java(fetchAccountReference(request.getAccount_id()))")
    public abstract BloodRequest toBloodRequest(BloodRequestCreationRequest request);

    protected Account fetchAccountReference(Long id){
        return entityManager.getReference(Account.class, id);
    }
}

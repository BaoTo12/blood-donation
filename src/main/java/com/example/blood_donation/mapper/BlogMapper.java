package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.dto.request.blog.BlogUpdateRequest;
import com.example.blood_donation.dto.response.blog.BlogResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.repository.AccountRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Mapping(target = "account", expression = "java(fetchAccount(request.getAccount_id()))")
    public abstract Blog toBlog(BlogCreationRequest request);

    protected Account fetchAccount(Long id){
        return accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found: " + id));
    }

    public abstract BlogResponse toBlogResponse(Blog blog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(BlogUpdateRequest request, @MappingTarget Blog blog);
}

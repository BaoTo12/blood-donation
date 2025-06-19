package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.request.CommentRequest;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.entity.Comment;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    private EntityManager entityManager;

    @Mapping(target = "account",  expression = "java(fetchAccountReference(request.getAccount_id()))")
    @Mapping(target = "blog", expression = "java(fetchBlogReference(request.getBlog_id()))")
    public abstract Comment toComment(CommentRequest request);


    protected Blog fetchBlogReference(Long id){
        return entityManager.getReference(Blog.class, id);
    }
    protected Account fetchAccountReference(Long id){
        return entityManager.getReference(Account.class, id);
    }
}

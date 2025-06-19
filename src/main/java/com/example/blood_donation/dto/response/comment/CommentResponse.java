package com.example.blood_donation.dto.response.comment;

import com.example.blood_donation.dto.response.account.AccountResponse;
import com.example.blood_donation.dto.response.blog.BlogResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Blog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CommentResponse {
    private BlogResponse blog;

    private AccountResponse account;

    private String content;

    private LocalDate created_at;
}

package com.example.blood_donation.dto.responses.comment;

import com.example.blood_donation.dto.responses.account.AccountResponse;
import com.example.blood_donation.dto.responses.blog.BlogResponse;
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

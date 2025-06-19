package com.example.blood_donation.dto.request;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {
    private Long blog_id;

    private Long account_id;

    private String content;
}

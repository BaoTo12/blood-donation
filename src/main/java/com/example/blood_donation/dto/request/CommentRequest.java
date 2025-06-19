package com.example.blood_donation.dto.request;



import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {
    @NotNull
    private Long blog_id;

    @NotNull
    private Long account_id;

    @NotNull
    private String content;
}

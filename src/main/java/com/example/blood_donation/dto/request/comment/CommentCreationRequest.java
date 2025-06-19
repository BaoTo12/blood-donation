package com.example.blood_donation.dto.request.comment;



import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentCreationRequest {
    @NotNull
    private Long blog_id;

    @NotNull
    private Long account_id;

    @NotNull
    private String content;
}

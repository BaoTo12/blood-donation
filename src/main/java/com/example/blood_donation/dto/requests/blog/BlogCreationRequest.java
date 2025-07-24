package com.example.blood_donation.dto.request.blog;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogCreationRequest {
    private Long account_id;
    @NotNull
    private String title;
    @NotNull
    private String content;
}

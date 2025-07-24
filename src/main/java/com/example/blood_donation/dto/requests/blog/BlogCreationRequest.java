package com.example.blood_donation.dto.requests.blog;


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

package com.example.blood_donation.dto.response.blog;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogResponse {
    @NotNull
    private String title;
    @NotNull
    private String content;
}

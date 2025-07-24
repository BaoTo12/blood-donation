package com.example.blood_donation.dto.request.blog;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogUpdateRequest {
    private String title;
    private String content;
}

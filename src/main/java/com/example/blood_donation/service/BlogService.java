package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;

public interface BlogService {
    Long createBlog(BlogCreationRequest request);
}

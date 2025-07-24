package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.blog.BlogCreationRequest;
import com.example.blood_donation.dto.requests.blog.BlogUpdateRequest;
import com.example.blood_donation.dto.responses.blog.BlogResponse;

import java.util.List;

public interface BlogService {
    Long createBlog(BlogCreationRequest request);

    List<BlogResponse> getAllBlogs();

    BlogResponse getBlogById(Long id);

    void deleteBlog(Long id);

    void updateBlog(Long id, BlogUpdateRequest request);
}

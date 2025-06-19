package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.dto.request.blog.BlogUpdateRequest;
import com.example.blood_donation.dto.response.blog.BlogResponse;

import java.util.List;

public interface BlogService {
    Long createBlog(BlogCreationRequest request);
    List<BlogResponse> getAllBlogs();
    BlogResponse getBlogById(Long id);
    void deleteBlog(Long id);
    void updateBlog(Long id, BlogUpdateRequest request);
}

package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.dto.request.blog.BlogUpdateRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.dto.response.blog.BlogResponse;
import com.example.blood_donation.service.BlogService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.base-path}/blog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('MEMBER')")
public class BlogController {

    BlogService blogService;

    @PostMapping
    public ApiResponse<Long> createBlog(@RequestBody @Valid BlogCreationRequest request) {
        Long id = blogService.createBlog(request);
        return ApiResponse.<Long>builder().result(id).build();
    }

    @GetMapping
    public ApiResponse<List<BlogResponse>> getAllBlogs() {
        return ApiResponse.<List<BlogResponse>>builder().result(blogService.getAllBlogs()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BlogResponse> getBlogById(@PathVariable Long id) {

        return ApiResponse.<BlogResponse>builder().result(blogService.getBlogById(id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ApiResponse.<String>builder().result("Deleted Blog Successfully").build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateBlog(@PathVariable Long id, @RequestBody BlogUpdateRequest request) {
        blogService.updateBlog(id, request);
        return ApiResponse.<String>builder().build();
    }
}

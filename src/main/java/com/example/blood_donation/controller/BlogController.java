package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.service.BlogService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.base-path}/blog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogController {

    BlogService blogService;

    @PostMapping
    public ApiResponse<Long> createBlog(@RequestBody @Valid BlogCreationRequest request) {
        Long id = blogService.createBlog(request);
        return ApiResponse.<Long>builder().result(id).build();
    }

}

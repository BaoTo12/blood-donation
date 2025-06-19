package com.example.blood_donation.controller;


import com.example.blood_donation.dto.request.comment.CommentCreationRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.dto.response.comment.CommentResponse;
import com.example.blood_donation.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping
    public ApiResponse<Long> createComment(@RequestBody @Valid CommentCreationRequest request){
        Long id = commentService.createComment(request);
        return ApiResponse.<Long>builder().result(id).build();
    }

    @GetMapping("/{blogId}")
    public ApiResponse<List<CommentResponse>> getCommentsByBlogId(@PathVariable Long blogId){
        return ApiResponse.<List<CommentResponse>>builder().result(commentService.getCommentByBlogId(blogId)).build();
    }
}

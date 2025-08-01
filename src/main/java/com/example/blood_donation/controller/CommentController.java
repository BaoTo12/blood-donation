package com.example.blood_donation.controller;


import com.example.blood_donation.dto.requests.comment.CommentCreationRequest;
import com.example.blood_donation.dto.requests.comment.CommentUpdateRequest;
import com.example.blood_donation.dto.responses.ApiResponse;
import com.example.blood_donation.dto.responses.comment.CommentResponse;
import com.example.blood_donation.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('MEMBER')")
public class CommentController {
    CommentService commentService;

    @PostMapping
    public ApiResponse<Long> createComment(@RequestBody @Valid CommentCreationRequest request) {
        Long id = commentService.createComment(request);
        return ApiResponse.<Long>builder().result(id).build();
    }

    @GetMapping("/{blogId}")
    public ApiResponse<List<CommentResponse>> getCommentsByBlogId(@PathVariable Long blogId) {
        return ApiResponse.<List<CommentResponse>>builder().result(commentService.getCommentByBlogId(blogId)).build();
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<?> updateComment(@PathVariable() Long commentId, @RequestBody CommentUpdateRequest request) {
        Integer result = commentService.updateComment(commentId, request);
        return ApiResponse.builder().result(result).build();
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.<String>builder().build();
    }
}

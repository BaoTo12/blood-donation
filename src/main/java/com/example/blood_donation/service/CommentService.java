package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.comment.CommentRequest;
import com.example.blood_donation.dto.response.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Long createComment(CommentRequest request);
    List<CommentResponse> getCommentByBlogId(Long id);
}

package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.comment.CommentCreationRequest;
import com.example.blood_donation.dto.request.comment.CommentUpdateRequest;
import com.example.blood_donation.dto.response.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Long createComment(CommentCreationRequest request);
    List<CommentResponse> getCommentByBlogId(Long id);
    Integer updateComment(Long id, CommentUpdateRequest request);
}

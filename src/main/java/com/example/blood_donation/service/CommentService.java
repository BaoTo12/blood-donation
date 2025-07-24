package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.comment.CommentCreationRequest;
import com.example.blood_donation.dto.requests.comment.CommentUpdateRequest;
import com.example.blood_donation.dto.responses.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Long createComment(CommentCreationRequest request);

    List<CommentResponse> getCommentByBlogId(Long id);

    Integer updateComment(Long id, CommentUpdateRequest request);

    void deleteComment(Long id);
}

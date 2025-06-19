package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.CommentRequest;

public interface CommentService {
    Long createComment(CommentRequest request);
}

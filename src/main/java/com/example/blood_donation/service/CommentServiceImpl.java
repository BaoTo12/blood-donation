package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.CommentRequest;
import com.example.blood_donation.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    @Override
    public Long createComment(CommentRequest request) {
        return 0L;
    }
}

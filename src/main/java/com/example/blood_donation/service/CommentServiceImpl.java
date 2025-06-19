package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.comment.CommentRequest;
import com.example.blood_donation.dto.response.comment.CommentResponse;
import com.example.blood_donation.entity.Comment;
import com.example.blood_donation.mapper.CommentMapper;
import com.example.blood_donation.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    CommentMapper mapper;

    @Override
    public Long createComment(CommentRequest request) {
        Comment comment = mapper.toComment(request);
        Comment savedComment;
        savedComment = commentRepository.save(comment);
        // this can throw SQLIntegrityConstraintViolationException
        return savedComment.getId();
    }

    @Override
    public List<CommentResponse> getCommentByBlogId(Long id) {
        List<Comment> comments = commentRepository.findCommentByBlogIdWithAccount(id);
        return comments.stream().map(mapper::toCommentResponse).toList();
    }
}

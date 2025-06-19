package com.example.blood_donation.controller;


import com.example.blood_donation.dto.request.CommentRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping
    public ApiResponse<Long> createComment(@RequestBody @Valid CommentRequest request){
        Long id = commentService.createComment(request);
        return ApiResponse.<Long>builder().result(id).build();
    }
}

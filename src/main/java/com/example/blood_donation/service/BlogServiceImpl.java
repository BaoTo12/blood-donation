package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.mapper.BlogMapper;
import com.example.blood_donation.repository.BlogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogServiceImpl implements BlogService {
    BlogRepository blogRepository;
    BlogMapper mapper;

    @Override
    public Long createBlog(BlogCreationRequest request) {
        Blog blog = mapper.toBlog(request);
        Blog savedBlog = blogRepository.save(blog);
        System.out.println("request: " + request);
        System.out.println("blog: " + blog);
        System.out.println("savedBlog: " + savedBlog);
        return savedBlog.getId();
    }
}


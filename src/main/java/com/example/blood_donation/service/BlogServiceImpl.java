package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.dto.response.blog.BlogResponse;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.BlogMapper;
import com.example.blood_donation.repository.BlogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(mapper::toBlogResponse).toList();
    }

    @Override
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found blog with Id: " + id));
        return mapper.toBlogResponse(blog);
    }
}


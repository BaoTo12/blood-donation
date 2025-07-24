package com.example.blood_donation.service.impl;

import com.example.blood_donation.dto.requests.blog.BlogCreationRequest;
import com.example.blood_donation.dto.requests.blog.BlogUpdateRequest;
import com.example.blood_donation.dto.responses.blog.BlogResponse;
import com.example.blood_donation.entity.Blog;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.BlogMapper;
import com.example.blood_donation.repository.BlogRepository;
import com.example.blood_donation.service.BlogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class BlogServiceImpl implements BlogService {
    BlogRepository blogRepository;
    BlogMapper mapper;

    @Override
    public Long createBlog(BlogCreationRequest request) {
        Blog blog = mapper.toBlog(request);
        Blog savedBlog = blogRepository.save(blog);
        return savedBlog.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(mapper::toBlogResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find blog with Id: " + id));
        return mapper.toBlogResponse(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public void updateBlog(Long id, BlogUpdateRequest request) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find blog with Id: " + id));
        mapper.updateFromDto(request, existingBlog);

        blogRepository.save(existingBlog);
    }
}


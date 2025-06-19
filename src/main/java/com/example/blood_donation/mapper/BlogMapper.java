package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.request.blog.BlogCreationRequest;
import com.example.blood_donation.entity.Blog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    Blog toBlog(BlogCreationRequest request);
}

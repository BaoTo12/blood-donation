package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}

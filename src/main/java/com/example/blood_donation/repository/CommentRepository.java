package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // This generates a SELECT with shared locks for reading
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.account WHERE c.blog.id = :blogId")
    List<Comment> findCommentByBlogIdWithAccount(@Param("blogId") Long id);

    @Query("SELECT c FROM Comment c WHERE c.blog.id = :blogId ORDER BY c.created_at DESC")
    List<Comment> findRecentCommentsByBlogId(@Param("blogId") Long blogId);

    // This method, when called within a transaction, will acquire appropriate locks
    @Modifying
    @Query("UPDATE Comment c SET c.content = :content WHERE c.id = :id ")
    Integer updateComment(@Param("content") String content, @Param("id") Long id);
}

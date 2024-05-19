package com.devco.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devco.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  List<Comment> findByPageId(String pageId);
}

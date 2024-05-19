package com.devco.blog.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devco.blog.dto.request.SaveCommentRequestDto;
import com.devco.blog.entity.Comment;
import com.devco.blog.entity.User;
import com.devco.blog.repository.CommentRepository;
import com.devco.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  public void saveComment(SaveCommentRequestDto requestDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = authentication.getPrincipal().toString();
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    Comment newComment = new Comment(requestDto, user);
    commentRepository.save(newComment);
  }

  public List<Comment> getComments(String pageId) {
    return commentRepository.findByPageId(pageId);
  }
}

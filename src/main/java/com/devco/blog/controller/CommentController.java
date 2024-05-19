package com.devco.blog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devco.blog.dto.request.GetCommentsRequestDto;
import com.devco.blog.dto.request.SaveCommentRequestDto;
import com.devco.blog.dto.response.CommentResponseDto;
import com.devco.blog.dto.response.GetCommentsResponseDto;
import com.devco.blog.entity.Comment;
import com.devco.blog.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
  private final CommentService commentService;

  @PostMapping()
  public ResponseEntity<JSONObject> saveComment(@RequestBody @Valid SaveCommentRequestDto requestBody) {
    commentService.saveComment(requestBody);
    return ResponseEntity.ok(new JSONObject());
  }

  @GetMapping()
  public GetCommentsResponseDto getComments(@Valid @ModelAttribute GetCommentsRequestDto param) {
    List<Comment> comments = commentService.getComments(param.getPageId());
    List<CommentResponseDto> commentDtos = comments.stream()
        .map(comment -> new CommentResponseDto(comment.getCommentId(), comment.getName(), comment.getContent()))
        .collect(Collectors.toList());
    return new GetCommentsResponseDto(commentDtos);
  }
}

package com.devco.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
  private int commentId;
  private String name;
  private String content;
}

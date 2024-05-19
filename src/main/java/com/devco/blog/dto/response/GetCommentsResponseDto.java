package com.devco.blog.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentsResponseDto {
  private List<CommentResponseDto> comments;
}

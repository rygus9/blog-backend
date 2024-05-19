package com.devco.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommentRequestDto {
  private String name;
  @NotBlank
  private String content;
  @NotBlank
  private String pageId;
}

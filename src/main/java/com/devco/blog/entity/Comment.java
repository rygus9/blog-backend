package com.devco.blog.entity;

import com.devco.blog.dto.request.SaveCommentRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int commentId;

  @NotNull
  private String content;
  private String name;

  @NotNull
  private String pageId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Comment(SaveCommentRequestDto requestDto, User user) {
    this.content = requestDto.getContent();
    this.name = requestDto.getName();
    this.user = user;
    this.pageId = requestDto.getPageId();
  }
}

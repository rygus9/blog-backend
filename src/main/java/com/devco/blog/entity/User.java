package com.devco.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Table
@Entity
public class User {
  @Id
  private String user_id;
  private String type;
  private String role;
}

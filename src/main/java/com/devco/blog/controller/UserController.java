package com.devco.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devco.blog.dto.response.ResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
  @GetMapping("/me")
  public ResponseEntity<ResponseDto> me() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}

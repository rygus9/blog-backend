package com.devco.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devco.blog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUserId(String UserId);
}

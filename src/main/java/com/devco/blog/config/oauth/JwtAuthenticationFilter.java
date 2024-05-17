package com.devco.blog.config.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import com.devco.blog.entity.User;
import com.devco.blog.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = parseCookiesToken(request);
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      String userId = jwtProvider.validate(token);
      if (userId == null) {
        filterChain.doFilter(request, response);
        return;
      }

      User user = userRepository.findByUserId(userId);
      String role = user.getRole();

      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(role));

      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

      AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null,
          authorities);
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      securityContext.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(securityContext);
    } catch (Exception e) {
      e.printStackTrace();
    }

    filterChain.doFilter(request, response);
  }

  private String parseCookiesToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("access_token".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

}

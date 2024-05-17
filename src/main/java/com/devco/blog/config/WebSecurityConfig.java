package com.devco.blog.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devco.blog.config.oauth.JwtAuthenticationFilter;
import com.devco.blog.config.oauth.OAuth2SuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
  private final DefaultOAuth2UserService oAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(cors -> cors.disable())
        .csrf(CsrfConfigurer::disable)
        .httpBasic(HttpBasicConfigurer::disable)
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> request
            .requestMatchers("/", "/api/v1/auth/**").permitAll()
            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .oauth2Login(oauth2 -> oauth2
            .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
            .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
            .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
            .successHandler(oAuth2SuccessHandler))
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    // {"code": "NP", "message": "No Permission"}
    response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission\"}");
  }
}
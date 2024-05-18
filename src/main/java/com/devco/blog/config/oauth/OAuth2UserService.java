package com.devco.blog.config.oauth;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.devco.blog.entity.User;
import com.devco.blog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(request);
    String oauthClientName = request.getClientRegistration().getClientName();

    try {
      System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
    } catch (Exception e) {
      e.printStackTrace();
    }

    User user = null;
    String userId = null;

    if (oauthClientName.equals("kakao")) {
      userId = "kakao_" + oAuth2User.getAttributes().get("id");
      user = new User(userId, "kakao", "ROLE_USER");
    }

    if (oauthClientName.equals("naver")) {
      Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
      userId = "naver_" + responseMap.get("id");
      user = new User(userId, "naver", "ROLE_USER");
    }

    if (user == null) {
      throw new OAuth2AuthenticationException("Unsupported provider");
    }

    userRepository.save(user);

    return new CustomOAuth2User(userId);
  }

}

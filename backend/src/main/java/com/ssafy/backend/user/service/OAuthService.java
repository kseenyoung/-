package com.ssafy.backend.user.service;

public interface OAuthService {

    String getToken(String code);

    String getUser(String token);
}

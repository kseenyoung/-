package com.ssafy.backend.loginhistory.service;

public interface LoginHistoryService {
    void failLogin(String loginUserId, String userIp);

    void successLogin(String loginUserId, String loginUserIp);
}

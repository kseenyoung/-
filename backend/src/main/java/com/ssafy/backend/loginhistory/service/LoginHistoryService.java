package com.ssafy.backend.loginhistory.service;

public interface LoginHistoryService {
    int failLogin(String loginUserId, String userIp);

    void successLogin(String loginUserId, String loginUserIp);
}

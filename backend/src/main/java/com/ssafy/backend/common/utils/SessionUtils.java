package com.ssafy.backend.common.utils;

import com.ssafy.backend.user.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("User");
    }

    public static String getSessionUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        return user.getUserId();
    }
}

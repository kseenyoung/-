package com.ssafy.backend.common.interceptor;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.user.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.ssafy.backend.common.response.BaseResponseStatus.NEED_AGAIN_LOGIN;
import static com.ssafy.backend.common.response.BaseResponseStatus.NEED_LOGIN;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuthInterceptor Auth Check");
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        System.out.println("AuthInterCeptor session Check  = " + session);
        if (session != null) {
            log.info("session Id : {}", session);
            User user = (User) session.getAttribute("User");
            if (user == null) throw new BaseException(NEED_AGAIN_LOGIN);
            String userId = user.getUserId();
            if (!"".equals(userId) && userId != null) {
                return true;
            }
        }
        throw new BaseException(NEED_LOGIN);
    }
}

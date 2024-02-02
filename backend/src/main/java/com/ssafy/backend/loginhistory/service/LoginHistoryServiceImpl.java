package com.ssafy.backend.loginhistory.service;

import com.ssafy.backend.loginhistory.model.mapper.LoginHistoryMapper;
import com.ssafy.backend.loginhistory.model.vo.LoginHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService{

    @Autowired
    LoginHistoryMapper loginHistoryMapper;
    
    private static final int WAIT_SECOND = 10;  // 대기 시간
    private static final int LIMIT = 5;   // 로그인 시도 제한 횟수
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
     * 로그인 실패했을 때 들어오는 메서드...
     */
    public int failLogin(String userId, String userIp){
        LoginHistoryVO loginHistoryVO = loginHistoryMapper.getLoginHistory(userIp, userId);
        System.out.println(loginHistoryVO);
        int remainTime = 0;
        if (loginHistoryVO == null){
            /*
             * userid, userip 쌍이 처음 로그인을 실패한 상황...
             * history 를 새로 insert 해줌...
             */
            loginHistoryMapper.addFirstHistory(userIp, userId);
        } else {
            /*
             * 로그인 이력이 있을 때 ...
             * try 가 LIMIT(5회) 가 됐으면 로그인 30초 동안 차단시킴.
             * 아니면 try+1 해서 table update
             */
            int tryLoginCount = loginHistoryVO.getTryLoginCount();
            tryLoginCount += 1;
            if (tryLoginCount > LIMIT){   // 로그인 시도 횟수가 5회 이상일 때 ...
                LocalDateTime lastLoginTime = LocalDateTime.parse(loginHistoryVO.getUpdatedDate(), formatter);
                LocalDateTime now = LocalDateTime.now();
                long diff = ChronoUnit.SECONDS.between(lastLoginTime, now);
                if (diff <= WAIT_SECOND){  // 대기 시간이 지나지 않았을 때...
                    // TODO : 로그인 차단
                    remainTime = 30-(int) diff;
                    return remainTime;
                } else {  // 대기 시간이 지났을 때 try 횟수를 초기화 시켜줌...
                    loginHistoryMapper.initializeTryLoginCount(userIp, userId);
                    return remainTime;
                }
            } else {   // 로그인 시도 횟수가 5회 미만...
                loginHistoryMapper.modifyTryLoginCount(userIp, userId);
            }
        }
        return remainTime;
    }

    /*
     * 로그인 실패했을 때 들어오는 메서드...
     */
    @Override
    public void successLogin(String userId, String userIp) {
        LoginHistoryVO loginHistoryVO = loginHistoryMapper.getLoginHistory(userIp, userId);
        if (loginHistoryVO!=null){
            // 로그인 이력이 있을 시에 지워준다...
            loginHistoryMapper.deleteLoginHistory(userIp, userId);
        }
    }
}

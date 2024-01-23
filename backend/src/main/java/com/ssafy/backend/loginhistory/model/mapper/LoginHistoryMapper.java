package com.ssafy.backend.loginhistory.model.mapper;

import com.ssafy.backend.loginhistory.model.dto.LoginHistoryDto;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Repository
public interface LoginHistoryMapper {
    LoginHistoryDto getLoginHistory(String userIp, String userId);

    void insertFirstHistory(String userIp, String userId);

    void deleteLoginHistory(String userIp, String userId);

    void initializeTryLoginCount(String userIp, String userId);

    void updateTryLoginCount(String userIp, String userId);
}

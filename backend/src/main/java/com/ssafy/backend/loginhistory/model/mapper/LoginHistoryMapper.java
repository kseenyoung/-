package com.ssafy.backend.loginhistory.model.mapper;

import com.ssafy.backend.loginhistory.model.vo.LoginHistoryVO;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Repository
public interface LoginHistoryMapper {
    LoginHistoryVO getLoginHistory(String userIp, String userId);

    void addFirstHistory(String userIp, String userId);

    void deleteLoginHistory(String userIp, String userId);

    void initializeTryLoginCount(String userIp, String userId);

    void modifyTryLoginCount(String userIp, String userId);
}

package com.ssafy.backend.user.model.mapper;

// 추후 JPA 로 전환 예정

import com.ssafy.backend.exception.MyException;
import com.ssafy.backend.user.model.UserSignupDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Mapper
@Repository
public interface UserMapper {
    void signup(UserSignupDto userSignupDto) throws Exception;

}

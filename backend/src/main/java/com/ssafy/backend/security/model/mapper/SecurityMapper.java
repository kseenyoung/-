package com.ssafy.backend.security.model.mapper;

import com.ssafy.backend.security.model.SecurityDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityMapper {
    void insertSalt(SecurityDto securityDto);
    String getSalt(String userId);

}

package com.ssafy.backend.security.model.mapper;

import com.ssafy.backend.security.model.dto.SecurityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityMapper {
    void addSalt(SecurityDTO securityDTO);
    String getSalt(String userId);
    void deleteSalt(String deleteUserId);
    void modifySalt(String originUserId, String newSalt);
}

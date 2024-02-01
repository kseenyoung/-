package com.ssafy.backend.security.model.dto;

import lombok.Data;

@Data
public class SecurityDTO {
    private String userId, salt;

    public SecurityDTO() {}

    public SecurityDTO(String userId, String salt){
        this.userId = userId;
        this.salt = salt;
    }


}

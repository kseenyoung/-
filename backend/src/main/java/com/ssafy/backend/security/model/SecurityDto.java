package com.ssafy.backend.security.model;

import lombok.Data;

@Data
public class SecurityDto {
    private String userId, salt;

    public SecurityDto() {}

    public SecurityDto(String userId, String salt){
        this.userId = userId;
        this.salt = salt;
    }


}

package com.ssafy.backend.loginhistory.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
public class LoginHistoryDto {
    private String user_id, user_ip;
    private int login_history_id, try_login_count;
    private String created_date, updated_date;
}

package com.ssafy.backend.user.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


//	`user_id`	varchar(20)	NOT NULL PRIMARY KEY,
//	`guild_id`	int	NULL,
//	`user_name`	varchar(20)	NOT NULL,
//	`modify_user_password_time`	timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//	`user_password`	varchar(200)	NOT NULL,
//	`user_phonenumber`	varchar(20)	NULL,
//	`user_birthday`	varchar(10)	NULL,
//	`user_email`	varchar(20)	NOT NULL,
//	`user_nickname`	char(10)	NULL,
//	`user_picture`	char(100)	NULL,
//	`user_point`	int	NOT NULL	DEFAULT 0,
//	`today_dagak_id`	int	NULL,
//	`user_status_message`	char(65)	NULL,
//	`created_date`	timestamp	DEFAULT CURRENT_TIMESTAMP
@Entity
@NoArgsConstructor
public class User {

    @Column
    @Id
    private String userId;

    @Column
    private String guildId;

    @Column
    private String userName;

    @Column
    private String userPassword;

    @Column
    private String userEmail;

    @Builder
    public User(String userId, String guildId, String userName, String userPassword, String userEmail) {
        this.userId = userId;
        this.guildId = guildId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }
}

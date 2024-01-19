package com.ssafy.backend.user.model.domain;

import com.ssafy.backend.user.model.UserSignupDto;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString
@Getter
public class User {

    @Column
    private Integer guildId;

    @Column
    private Integer userPoint;

    @Column
    private String userPassword,  userName,
            modifyUserPasswordTime, userPhonenumber, userBirthday,
    userEmail, userNickname, userPicture,  todayDagakId, userStatusMessage;

    @Column
    private LocalDateTime createdDate;

    @Column
    @Id
    private String userId;

    public boolean checkPassword(String encryptedPassword) {
        return this.userPassword.equals(encryptedPassword);
    }

}

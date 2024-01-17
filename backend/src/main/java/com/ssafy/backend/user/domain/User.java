package com.ssafy.backend.user.domain;

import com.ssafy.backend.mokkoji.domain.Mokkoji;
import com.ssafy.backend.user.model.UserSignupDto;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    private Integer userPoint;;

    @ManyToOne
    @JoinColumn(name = "mokkojiId")
    private Mokkoji mokkojiId;

    @Column
    private String userPassword,  userName,
            modifyUserPasswordTime, userPhonenumber, userBirthday,
    userEmail, userNickname, userPicture,  todayDagakId, userStatusMessage;
    @Column
    private LocalDateTime createdDate;

    @Column
    @Id
    private String userId;

    public UserSignupDto toDto(){
        return new UserSignupDto(userId);
    }


}

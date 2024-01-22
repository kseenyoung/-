package com.ssafy.backend.user.model.domain;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.vo.UserViewVO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString
@Getter
public class User {

    @Column
    private Integer userPoint, userTotalStudyTime;

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

    public boolean checkPassword(String encryptedPassword) {
        return this.userPassword.equals(encryptedPassword);
    }

    public void usePoint(int userPoint) {
        this.userPoint -= userPoint;
    }

    public void saveMokkoji(Mokkoji mokkoji) {
        this.mokkojiId = mokkoji;
    }

}

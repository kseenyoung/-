package com.ssafy.backend.user.model;


import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.RegEx;
import com.ssafy.backend.user.model.domain.User;
import org.springframework.http.HttpStatus;


// 나중에 유효성 검사 주석 풀어야 함.
public class UserSignupDto {
    private String userId, userBirthday, userName, userPassword, userPhonenumber, userEmail, userNickname;

    public UserSignupDto(String userId, String userBirthday, String userName, String userPassword, String userPhonenumber, String userEmail, String userNickname) throws MyException {
        setUserId(userId);
        setUserBirthday(userBirthday);
        setUserName(userName);
        setUserPassword(userPassword);
        setUserPhonenumber(userPhonenumber);
        setUserEmail(userEmail);
        setUserNickname(userNickname);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) throws MyException {
        if (userId == null) {
            throw new MyException("유저 아이디가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        RegEx.isValidUserId(userId);
        this.userId = userId;
    }

    public String getUserBirthday() {
        return this.userBirthday;
    }

    public void setUserBirthday(String userBirthday) throws MyException {
        if (userBirthday == null) {
            throw new MyException("생일이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        this.userBirthday = userBirthday;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) throws MyException {
        if (userName == null) {
            throw new MyException("이름이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }



    // TODO : 정규표현식 주석 풀기.
    public void setUserPassword(String userPassword) throws MyException {
        if (userPassword == null) {
            throw new MyException("비밀번호가 없습니다..", HttpStatus.BAD_REQUEST);
        }
//        RegEx.isValidUserPassword(userPassword);
        this.userPassword = userPassword;
    }

    public String getUserPhonenumber() {
        return this.userPhonenumber;
    }


    public void setUserPhonenumber(String userPhonenumber) throws MyException {
        if (userPhonenumber == null) {
            throw new MyException("핸드폰 번호가 없습니다..", HttpStatus.BAD_REQUEST);
        }
        this.userPhonenumber = userPhonenumber;
    }

    public String getUserEmail() {
        return userEmail;
    }


    public void setUserEmail(String userEmail) throws MyException {
        if (userEmail == null) {
            throw new MyException("이메일이 없습니다..", HttpStatus.BAD_REQUEST);
        }
        RegEx.isValidUserEmail(userEmail);
        this.userEmail = userEmail;
    }

    public String getUserNickname() {
        return userNickname;
    }



    // TODO : 정규표현식 주석 풀기
    public void setUserNickname(String userNickname) throws MyException {
        if (userNickname == null) {
            throw new MyException("닉네임이 없습니다..", HttpStatus.BAD_REQUEST);
        }
//        RegEx.isValidUserNickname(userNickname);
        this.userNickname = userNickname;
    }

    public User toEntity() {
        return User.builder().
                userId(this.userId).
                userPassword(this.userPassword).
                userName(this.userName).
                userEmail(this.userEmail).build();
    }
    public UserSignupDto(){}
    public UserSignupDto(String userId) {
        this.userId = userId;
    }
}

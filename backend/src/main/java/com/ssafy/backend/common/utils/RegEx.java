package com.ssafy.backend.common.utils;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_EMAIL;

public class RegEx {
    // 아이디는 5글자 이상, 15글자 이하여야 하고 영문소문자와 숫자만 가능하다.
    public static void isValidUserId(String user_id) throws MyException {
        if(!Pattern.matches("^[a-z0-9]{5,15}$", user_id)){
            throw new MyException("유저 아이디 정규표현식을 따라주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    // 비밀번호는 8글자 이상이어야 하며 특수문자를 포함해야한다.
    public static void isValidUserPassword(String user_password) throws MyException{
        if(!Pattern.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$", user_password)){
            throw new MyException("유저 비밀번호 정규표현식을 따라주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    // 닉네임은 2글자 이상, 8글자 이하여야 하며 특수문자는 사용할 수 없어요
    public static void isValidUserNickname(String user_nickname) throws MyException{
        if(!Pattern.matches("^[가-힣a-zA-Z0-9]{2,8}$", user_nickname)){
            throw new MyException("유저 닉네임 정규표현식을 따라주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    public  static void isValidUserEmail(String user_email) throws MyException{
        if(!Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){0,9}\\.[a-zA-Z]{2,3}$", user_email)){
            throw new MyException("올바른 이메일 형식이 아닙니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
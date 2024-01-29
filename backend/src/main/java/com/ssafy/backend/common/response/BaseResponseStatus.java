package com.ssafy.backend.common.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_LOGIN(true, 1001, "로그인에 성공하였습니다."),
    SUCCESS_ID_CHECK(true, 1002, "사용 가능한 아이디입니다."),
    SUCCESS_ID_SIGN_UP(true, 1003, "회원 가입에 성공했습니다."),
    SUCCESS_NICKNAME_CHECK(true, 1004, "사용 가능한 닉네임입니다."),
    SUCCESS_SEND_EMAIL(true, 1005, "이메일 전송에 성공했습니다."),
    SUCCESS_AUTH(true, 1006, "인증에 성공했습니다."),
    SUCCESS_DELETE_USER(true, 1007, "회원 탈퇴 성공."),
    SUCCESS_CHANGE_PASSWORD(true, 1008, "비밀번호 변경에 성공했습니다."),
    SUCCESS_CHANGE_NICKNAME(true, 1009, "닉네임 변경에 성공했습니다."),


    SUCCESS_DELETE_MOKKOJI(true, 1100, "모꼬지를 정상적으로 삭제했습니다"),
    SUCCESS_KICK_MOKKOJI_MEMBER(true, 1101, "모꼬지 회원을 정상적으로 강퇴했습니다"),
    SUCCESS_CREATE_MOKKOJI(true, 1102, "모꼬지를 정상적으로 생성했습니다"),
    SUCCESS_LEAVE_MOKKOJI(true, 1103, "모꼬지를 정상적으로 떠났습니다"),
    SUCCESS_APPLY_FOR_MOKKOJI(true, 1104, "모꼬지를 가입신청이 되었습니다"),
    SUCCESS_ACCEPT_MOKKOJI(true, 1105, "모꼬지가입을 승인하였습니다"),
    SUCCESS_TAG_CREATE(true, 1106, "게시판 태그를 생성하였습니다"),


    SUCCESS_CREATE_TOKEN(true, 1200, "토큰 발급에 성공했습니다."),

    /**
     * 2000 : Request 오류
     */
    EMPTY_SIGN(false, 2000, "sign 값이 없습니다."),
    EMPTY_SESSION(false, 2001, "header에 SESSION ID가 없습니다."),
    ALREADY_EXIST_EMAIL(false, 2003, "이미 존재하는 이메일입니다."),
    NOT_MATCH_CHECK_PASSWORD(false, 2004, "비밀번호 확인란을 다시 확인해주시기 바랍니다."),
    NOT_EXIST_EMAIL(false, 2005, "존재하지 않는 이메일 주소입니다."),
    NOT_MATCH_PASSWORD(false, 2006, "비밀번호가 일치하지 않습니다."),
    INVALID_AUTH_TOKEN(false, 2007, "유효하지 않은 인증번호입니다."),
    NOT_AUTH_PHONE_NUMBER(false, 2008, "인증이 되지 않은 전화번호입니다."),
    ALREADY_AUTH_PHONE_NUMBER(false, 2008, "이미 인증을 받은 전화번호입니다."),
    NOT_EXIST_MEMBER(false, 2009, "존재하지 않는 사용자입니다."),
    ALREADY_EXIST_PHONE_NUMBER(false, 2010, "이미 등록된 휴대전화 번호입니다."),
    NOT_EXIST_GROUP(false, 2011, "존재하지 않는 모꼬지입니다."),
    ALREADY_EXIST_GROUP(false, 2012, "이미 존재하는 모꼬지입니다."),
    ALREADY_EXIST_CONTACT_NUMBER(false, 2013, "이미 등록된 연락처입니다."),
    NOT_EXIST_CONTACT_NUMBER(false, 2014, "존재하지 않는 연락처입니다."),
    NOT_ACCESS_GOOGLE(false, 2015, "카카오톡 로그인 인증에 실패했습니다."),
    NOT_MATCH_MEMBER(false, 2016, "해당 사용자의 데이터가 아닙니다."),
    NOT_EXIST_SENDER_NUMBER(false, 2017, "존재하지 않는 발신자 번호입니다."),
    NOT_AUTH_MEMBER(false, 2018, "권한이 없는 사용자입니다."),
    NOT_MATCH_SENDER_NUMBER(false, 2021, "해당 사용자의 발신자 번호가 아닙니다."),
    NOT_EXIST_MESSAGE(false, 2023, "존재하는 메시지가 아닙니다."),
    NOT_EXIST_TEMPLATE(true, 2025, "존재하지 않는 다각입니다."),
    ALREADY_CANCEL_RESERVE(false, 2027, "이미 취소된 예약 메시지입니다."),
    NOT_EXIST_POINT(false, 2028, "존재하지 않는 포인트입니다."),
    INSUFFICIENT_POINT(false, 2029, "포인트가 부족합니다."),
    NOT_MATCH_SIGN(false, 2030, "일치하는 sign 정보가 없습니다."),
    NOT_EXIST_TAG_ID(false, 2031, "일치하는 태그 번호가 없습니다."),
    NOT_EXIST_ALARM_ID(false, 2032, "일치하는 알람 아이디가 없습니다."),


    /* 알람 서비스 관련 에러 이넘 클래스 BAD REQUEST*/
    ALREADY_DELETE_ALARM(false, 2033, "이미 삭제된 알람입니다"),

    ALREADY_EXIST_USER(false, 2034, "이미 존재하는 회원입니다."),
    ALREADY_EXIST_ID(false, 2035, "이미 존재하는 아이디입니다."),
    FAIL_LOGIN(false, 2036, "로그인에 실패했습니다."),
    FAIL_SIGN_UP(false, 2037, "회원 가입에 실패했습니다."),
    ALREADY_EXIST_NICKNAME(false, 2038, "이미 존재하는 닉네임입니다."),
    PLZ_ENTER_NICKNAME(false, 2039, "닉네임을 입력해주세요.."),
    INVALID_AUTH_CODE(false, 2040, "올바른 인증번호를 입력해주세요."),

    FAIL_TO_DELETE_USER(false, 2041, "회원 탈퇴 실패."),


    NEED_LOGIN(false, 2042,"로그인이 필요한 서비스입니다."),
    NEED_KAKAO_LINK(false, 2043,"기존 회원 로그인 후 카카오 서비스와 연동 하시겠습니까?"),
    NEED_GOOGLE_LINK(false, 2044,"기존 회원 로그인 후 구글 서비스와 연동 하시겠습니까?"),
    

    /* 모꼬지 관련 에러 이넘 클래스 BAD REQUEST*/
    AVOID_DUPLICATE_ALARM(false, 2101, "이미 상대방에게 요청을 보냈습니다."),
    ALREADY_EXIST_USER_MOKKOJI(false, 2100, "이미 회원은 모꼬지를 가입한 상태입니다."),
    NOT_EXIST_KICK_USER(false, 2102, "강퇴할 모꼬지 회원이 존재하지 않습니다."),
    NOT_FOUND_RANKING_MOKKOJI(false, 2103, "해당 모꼬지의 랭킹이 존재하지 않습니다"),
    NOT_EXIST_USER_MOKKOJI(false, 2104, "회원의 모꼬지가 존재하지 않습니다"),
    NOT_LEAVE_MOKKOJI_KING(false, 2105, "모꼬지장은 탈퇴할 수 없습니다"),
    IS_EXIST_MOKKOJI_NAME(false, 2106, "이미 존재하는 모꼬지 이름입니다"),
    NOT_EXIST_MOKKOJI(false, 2107, "해당 모꼬지를 찾을 수 없습니다"),


    /* 친구 관련 에러 이넘 클래스 BAD REQUEST*/
    ALREADY_EXIST_FRIEND(false, 2200, "이미 상대방과 친구입니다."),
    NOT_FRIEND(false, 2200, "이미 상대방과 친구입니다."),


    /**
     * 2500 : Request 성공
     */
    IMAGE_UPLOAD_SUCCESS(true, 2500, "이미지 업로드에 성공하였습니다."),
    SEND_MESSAGE_SUCCESS(true, 2501, "인증 번호 발송에 성공했습니다."),
    PHONE_NUMBER_AUTH_SUCCESS(true, 2502, "핸드폰 번호 인증에 성공하였습니다."),
    EMAIL_AUTH_SUCCESS(true, 2502, "핸드폰 번호 인증에 성공하였습니다."),


    /**
     * 3000 : Response 오류
     */
    VALIDATED_ERROR(false, 3000, "VALIDATED_ERROR"), // @Valid 예외 처리
    SEND_MESSAGE_ERROR(false, 3001, "메시지를 발송하는 과정 중 오류가 발생했습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    INTERNAL_SERVER_ERROR(false, 4000, "서버 오류입니다"),
    JSON_PROCESSING_ERROR(false, 4001, "JSON을 처리하는 과정 중 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(false, 4002, "파일을 업로드 하는 과정 중에 에러가 발생했습니다."),

    /**
     * 5000 : 잡지 못 한 서버 오류
     */
    OOPS(false, 5000, "Oops..."),
    ;

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}

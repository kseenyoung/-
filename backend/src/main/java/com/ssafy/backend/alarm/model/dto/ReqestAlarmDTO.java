package com.ssafy.backend.alarm.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@Getter
@Setter
@ToString
public class ReqestAlarmDTO {

    private String userId, requestedUserId;
    private Integer tagId;

    public ReqestAlarmDTO(String userId, String requestedUserId, String tagId) {
        setUserId(userId);
        setRequestedUserId(requestedUserId);
        setTagId(tagId);
    }
    @Builder
    public ReqestAlarmDTO(String userId, String requestedUserId, Integer tagId) {
        this.userId = userId;
        this.requestedUserId = requestedUserId;
        this.tagId = tagId;
    }

    public void setUserId(String userId) {
        if(userId == null)
            throw new BaseException(EMPTY_SIGN);
        // TODO 존재하는 아이디 확인
        this.userId = userId;
    }

    public void setRequestedUserId(String requestedUserId) {
        // TODO 존재하는 유저 아이디 확인
        this.requestedUserId = requestedUserId;
    }

    public void setTagId(String tagId) {
        // TODO 존재하는 태그 아이디인지 확인
        try{
            this.tagId = Integer.parseInt(tagId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(NOT_EXIST_TAG_ID);
        }
    }
}

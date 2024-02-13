package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_DATA;
import static com.ssafy.backend.common.response.BaseResponseStatus.WRONG_TYPE;

@Getter
@Slf4j
public class DeleteCalendarDagakDTO {
    private String userId;
    private Integer calendarDagakId;

    public DeleteCalendarDagakDTO(String userId, String calendarDagakId) {
        setUserId(userId);
        setCalendarDagakId(calendarDagakId);
    }

    public void setUserId(String userId) {
        if(userId == null)
            throw new BaseException(NOT_EXIST_DATA);
        this.userId = userId;
    }

    public void setCalendarDagakId(String calendarDagakId) {
        try{
            log.info("calendarDagakId : {}", calendarDagakId);
            this.calendarDagakId = Integer.parseInt(calendarDagakId);
        } catch (RuntimeException e){
            throw new BaseException(WRONG_TYPE);
        }
    }
}

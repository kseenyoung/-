package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.backend.common.response.BaseResponseStatus.JSON_PARSING_ERROR;

@Getter
@Slf4j
public class UpdateMemoryTimeDTO {

    private Integer gakId, categoryId, calendarId;
    private Integer memoryTime;
    private String userId;

    public UpdateMemoryTimeDTO() {
    }

    public UpdateMemoryTimeDTO(String gakId, String categoryId, String calendarId, Integer memoryTime, String userId) {
        setGakId(gakId);
        setMemoryTime(memoryTime);
        setUserId(userId);
        setCategoryId(categoryId);
        setCalendarId(calendarId);
    }

    private void setCalendarId(String calendarId) {
        if(calendarId == null)
            throw new BaseException(JSON_PARSING_ERROR);
        try{
            this.calendarId = Integer.parseInt(calendarId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(JSON_PARSING_ERROR);
        }
    }

    private void setCategoryId(String categoryId) {
        if(categoryId == null)
            throw new BaseException(JSON_PARSING_ERROR);
        try{
            this.categoryId = Integer.parseInt(categoryId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(JSON_PARSING_ERROR);
        }
    }

    private void setUserId(String userId) {
        if(userId == null)
            throw new BaseException(JSON_PARSING_ERROR);
        this.userId = userId;
    }

    public void setGakId(String gakId) {
        if(gakId == null)
            throw new BaseException(JSON_PARSING_ERROR);
        try{
            this.gakId = Integer.parseInt(gakId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(JSON_PARSING_ERROR);
        }
    }

    public void setMemoryTime(Integer memoryTime) {
        if(gakId == null)
            throw new BaseException(JSON_PARSING_ERROR);
        this.memoryTime = memoryTime;
    }

    @Override
    public String toString() {
        return "UpdateMemoryTimeDTO{" +
                "gakId=" + gakId +
                ", categoryId=" + categoryId +
                ", calendarId=" + calendarId +
                ", memoryTime=" + memoryTime +
                ", userId='" + userId + '\'' +
                '}';
    }
}

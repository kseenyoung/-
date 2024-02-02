package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDate;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_MATCH_SIGN;
import static com.ssafy.backend.common.response.BaseResponseStatus.WRONG_TYPE;

@Getter
public class AddDagakDateDTO {

    private Integer dagakId;

    private LocalDate calendarDate;

    private String userId;

    public AddDagakDateDTO() {
    }

    public AddDagakDateDTO(String userId, String dagakId, LocalDate calendarDate) {
        setUserId(userId);
        setDagakId(dagakId);
        setCalendarDate(calendarDate);
    }

    public void setUserId(String userId) {
        if(userId == null)
            throw new BaseException(NOT_MATCH_SIGN);
        this.userId = userId;
    }

    public void setDagakId(String dagakId) {
        if(dagakId == null)
            throw new BaseException(NOT_MATCH_SIGN);
        try{
            this.dagakId = Integer.parseInt(dagakId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(WRONG_TYPE);
        }
    }

    public void setCalendarDate(LocalDate calendarDate) {
        if(dagakId == null)
            throw new BaseException(NOT_MATCH_SIGN);
        this.calendarDate = calendarDate;
    }

    @Override
    public String toString() {
        return "AddDagakDateDTO{" +
                "dagakId=" + dagakId +
                ", calendarDate=" + calendarDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}

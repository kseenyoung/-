package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

import static com.ssafy.backend.common.response.BaseResponseStatus.WRONG_TYPE;

@Getter
@Setter
@ToString
public class GakDto {

    @Id
    @Column
    private Integer gakId;

    @Column
    private Integer dagakId, categoryId, gakOrder, runningTime;

    @Column
    private String userId;


    public GakDto(String categoryId, Integer gakOrder, String runningTime, String userId) {
        setCategoryId(categoryId);
        setGakOrder(gakOrder);
        setRunningTime(runningTime);
        setUserId(userId);
    }

    public void setGakId(Integer gakId) {
        this.gakId = gakId;
    }

    public void setDagakId(Integer dagakId) {
        this.dagakId = dagakId;
    }

    public void setCategoryId(String categoryId) {
        try{
            this.categoryId = Integer.parseInt(categoryId);
        } catch(RuntimeException e){
            throw new BaseException(WRONG_TYPE);
        }
    }

    public void setRunningTime(String runningTime) {
        try{
            this.runningTime = Integer.parseInt(runningTime);
        } catch(RuntimeException e){
            throw new BaseException(WRONG_TYPE);
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

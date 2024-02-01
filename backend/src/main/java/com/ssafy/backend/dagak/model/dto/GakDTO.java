package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;

import static com.ssafy.backend.common.response.BaseResponseStatus.WRONG_TYPE;

@Getter
public class GakDTO {

    private Integer gakId;

    private Integer dagakId, categoryId, gakOrder, runningTime;

    private String userId;


    public GakDTO(String categoryId, Integer gakOrder, String runningTime, String userId) {
        setCategoryId(categoryId);
        setGakOrder(gakOrder);
        setRunningTime(runningTime);
        setUserId(userId);
    }

    public GakDTO(Integer categoryId, Integer gakOrder, Integer runningTime, String userId){
        this.categoryId = categoryId;
        this.gakOrder = gakOrder;
        this.runningTime = runningTime;
        this.userId = userId;
    }

    public GakDTO(Integer remainGakId, Integer remainOrder) {
        this.gakId = remainGakId;
        this.gakOrder = remainOrder;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setGakOrder(Integer gakOrder) {
        this.gakOrder = gakOrder;
    }

    public void setRunningTime(Integer runningTime) {
        this.runningTime = runningTime;
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

    @Override
    public String toString() {
        return "GakDTO{" +
                "gakId=" + gakId +
                ", dagakId=" + dagakId +
                ", categoryId=" + categoryId +
                ", gakOrder=" + gakOrder +
                ", runningTime=" + runningTime +
                ", userId='" + userId + '\'' +
                '}';
    }
}

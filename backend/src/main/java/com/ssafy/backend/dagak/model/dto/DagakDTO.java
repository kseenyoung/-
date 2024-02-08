package com.ssafy.backend.dagak.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class DagakDTO {

    private String userId, dagakName;

    private Integer totalTime;

    public DagakDTO() {
    }

    public DagakDTO(String userId, Integer totalTime, String dagakName) {
        setUserId(userId);
        setTotalTime(totalTime);
        setDagakName(dagakName);
    }

    public void setUserId(String userId) {
        if(userId == null)
            throw new BaseException(BaseResponseStatus.NOT_EXIST_DATA);
        this.userId = userId;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public void setDagakName(String dagakName) {
        if(dagakName == null)
            throw new BaseException(BaseResponseStatus.NOT_EXIST_DATA);
        this.dagakName = dagakName;
    }

    @Override
    public String toString() {
        return "DagakDTO{" +
                "userId='" + userId + '\'' +
                ", dagakName='" + dagakName + '\'' +
                ", totalTime=" + totalTime +
                '}';
    }
}

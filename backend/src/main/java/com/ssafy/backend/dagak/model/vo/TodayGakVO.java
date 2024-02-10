package com.ssafy.backend.dagak.model.vo;

import lombok.Data;

@Data
public class TodayGakVO {
    private Integer gakId, totalTime, memoryTime, calendarId, categoryId, requiredStudyTime, gakOrder;
    private String userId, categoryName;
}

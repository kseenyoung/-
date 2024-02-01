package com.ssafy.backend.dagak.model.mapper;

import com.ssafy.backend.dagak.model.domain.Calendar;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DagakMapper {

    Calendar getCalendarByCalendarDate(String calendarDate);
}

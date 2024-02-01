package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.domain.Calendar;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.dto.RegisterDagakDto;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;

import java.time.LocalDate;
import java.util.List;

public interface DagakService {
    int createDagak(DagakDto dagakDto);

    void createGak(List<GakDto> gaks);

    List<CalendarDagakVO> getCalendar(String userId);

    List<CalendarDagakVO> getCalendarGaks(List<CalendarDagakVO> calendarDagaks);

    List<Dagak> getDagakList(String userId);

    List<Gak> getGakInformation(Integer dagakId);

    CalendarDagakVO getDagak(String userId, LocalDate today);


    void updateGak(Integer dagakId, Integer gakId, Integer categoryId, Integer runningTime);


    void deleteGak(Integer deleteGakId);


    void registerDagak(RegisterDagakDto registerDagakDto);

    boolean isExistDagakId(Integer dagakId);
    void updateGakOrder(List<GakDto> remainGaks);

    void deleteDagak(Integer deleteDagakId);
}


package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDTO;
import com.ssafy.backend.dagak.model.dto.GakDTO;
import com.ssafy.backend.dagak.model.dto.AddDagakDateDTO;
import com.ssafy.backend.dagak.model.dto.UpdateMemoryTimeDTO;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;

import java.time.LocalDate;
import java.util.List;

public interface DagakService {
    int addDagak(DagakDTO dagakDto);

    void createGak(List<GakDTO> gaks);

    List<CalendarDagakVO> getCalendarList(String userId);

    List<CalendarDagakVO> getCalendarGaks(List<CalendarDagakVO> calendarDagaks);

    List<Dagak> getDagakList(String userId);

    List<Gak> getGakInformation(Integer dagakId);

    CalendarDagakVO getDagak(String userId, LocalDate today);

    void modifyGak(Integer dagakId, Integer gakId, Integer categoryId, Integer runningTime);

    void deleteGak(Integer deleteGakId);

    void addDagakDate(AddDagakDateDTO addDagakDateDto);

    boolean isExistDagakId(Integer dagakId);

    void modifyGakOrder(List<GakDTO> remainGaks);

    void deleteDagak(Integer deleteDagakId);

    void modifyMemoryTime(UpdateMemoryTimeDTO updateStartTimeDto);
}


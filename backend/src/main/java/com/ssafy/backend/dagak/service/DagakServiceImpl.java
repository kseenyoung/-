package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.domain.Calendar;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.repository.CalendarRepository;
import com.ssafy.backend.dagak.model.repository.DagakRepository;
import com.ssafy.backend.dagak.model.repository.GakRepository;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DagakServiceImpl implements DagakService {

    @Autowired
    DagakRepository dagakRepository;

    @Autowired
    GakRepository gakRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Override
    public int createDagak(DagakDto dagakDto) {
        //log.info("dagak : {}", dagakDto);
        return dagakRepository.save(
                Dagak.builder()
                        .userId(dagakDto.getUserId())
                        .totalTime(dagakDto.getTotalTime())
                        .build()
        ).getDagakId();
    }

    @Override
    public void createGak(List<GakDto> gaks) {
        //log.info("gak : {}", gaks);
        List<Gak> gakEntities = new ArrayList<>();
        for (GakDto gak : gaks) {
            Gak build = Gak.builder()
                    .userId(gak.getUserId())
                    .gakOrder(gak.getGakOrder())
                    .runningTime(gak.getRunningTime())
                    .dagakId(gak.getDagakId())
                    .build();
            gakEntities.add(build);
        }

        gakRepository.saveAll(gakEntities);
    }

    @Override
    public List<CalendarDagakVO> getCalendar(String userId) {
        List<CalendarDagakVO> result = new ArrayList<>();

        List<Calendar> byUserId = calendarRepository.findByUserId(userId);
        for(Calendar calendar: byUserId){
            CalendarDagakVO calendarDagakVO = new CalendarDagakVO(calendar.getCalendarDagakId(), calendar.getDagakId(), calendar.getCalendarDate());
            result.add(calendarDagakVO);
        }
//            log.info("**********calendarDagakVO : {}", result);
        return result;
    }

    @Override
    public List<CalendarDagakVO> getCalendarGaks(List<CalendarDagakVO> calendarDagaks) {

        for(CalendarDagakVO calendarDagakVO: calendarDagaks){
//            log.info("dagakId : {}", calendarDagakVO.getDagakId());
            List<Gak> allByDagakId = gakRepository.findAllByDagakId(calendarDagakVO.getDagakId());
            calendarDagakVO.setGaks(allByDagakId);
//            log.info("gaks : {}", allByDagakId);
        }

        return calendarDagaks;
    }


}
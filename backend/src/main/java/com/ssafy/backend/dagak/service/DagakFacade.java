package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.repository.GakRepository;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DagakFacade {

    @Autowired
    private DagakService dagakService;

    @Autowired
    GakRepository gakRepository;

    @Transactional
    public void createDagak(DagakDto dagakDto, List<GakDto> gaks){

        int dagakId = dagakService.createDagak(dagakDto);

        // 각 생성
        for(GakDto gak: gaks){
            gak.setDagakId(dagakId);
        }

        dagakService.createGak(gaks);
    }

    @Transactional
    public List<CalendarDagakVO> getCalendarDagaks(String userId) {
        // CalendarDagak 가져오기
        List<CalendarDagakVO> calendarDagaks = dagakService.getCalendar(userId);
        // dagakId로 gak들 정보 가져오기
        dagakService.getCalendarGaks(calendarDagaks);

//        for(CalendarDagakVO vo : calendarDagaks){
//            log.info("===== CalendarDagakVO : {}", vo);
//        }
//        log.info("최종 CalendarDagakVOS : {}" , calendarDagaks);

        return calendarDagaks;

    }


    @Transactional
    public void deleteGak(Integer deleteGakId, List<Map<String, Integer>> remainGakInformation) {
        dagakService.deleteGak(deleteGakId);
        List<GakDto> remainGaks = new ArrayList<>();
        if (!remainGakInformation.isEmpty()){
            for(Map<String, Integer> gak: remainGakInformation){
                Integer remainGakId = gak.get("gakId");
                Integer remainOrder = gak.get("gakOrder");
                remainGaks.add(new GakDto(remainGakId, remainOrder));
            }
                    dagakService.updateGakOrder(remainGaks);
        }
    }

    @Transactional
    public void deleteDagak(Integer deleteDagakId) {
        dagakService.deleteDagak(deleteDagakId);
        List<Gak> gaks = gakRepository.findAllByDagakId(deleteDagakId);
        for (Gak gak : gaks){
            dagakService.deleteGak(gak.getGakId());
        }
    }
}

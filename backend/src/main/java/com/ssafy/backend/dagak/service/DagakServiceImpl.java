package com.ssafy.backend.dagak.service;

import com.ssafy.backend.category.model.repository.CategoryRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.dagak.model.domain.Calendar;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.dto.RegisterDagakDto;
import com.ssafy.backend.dagak.model.mapper.DagakMapper;
import com.ssafy.backend.dagak.model.repository.CalendarRepository;
import com.ssafy.backend.dagak.model.repository.DagakRepository;
import com.ssafy.backend.dagak.model.repository.GakRepository;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_DAGAK;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_GAK;

@Service
@Slf4j
public class DagakServiceImpl implements DagakService {

    @Autowired
    DagakRepository dagakRepository;

    @Autowired
    GakRepository gakRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DagakMapper dagakMapper;

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
        for (Calendar calendar : byUserId) {
            CalendarDagakVO calendarDagakVO = new CalendarDagakVO(calendar.getCalendarDagakId(), calendar.getDagakId(), calendar.getCalendarDate());
            result.add(calendarDagakVO);
        }
//            log.info("**********calendarDagakVO : {}", result);
        return result;
    }

    @Override
    public List<CalendarDagakVO> getCalendarGaks(List<CalendarDagakVO> calendarDagaks) {

        for (CalendarDagakVO calendarDagakVO : calendarDagaks) {
//            log.info("dagakId : {}", calendarDagakVO.getDagakId());
            List<Gak> allByDagakId = gakRepository.findAllByDagakId(calendarDagakVO.getDagakId());
            calendarDagakVO.setGaks(allByDagakId);
//            log.info("gaks : {}", allByDagakId);
        }

        return calendarDagaks;
    }

    @Override
    public List<Dagak> getDagakList(String userId) {
        return dagakRepository.findDagaksByUserId(userId);
    }

    @Override
    public List<Gak> getGakInformation(Integer dagakId) {
        return gakRepository.findAllByDagakId(dagakId);
    }

    @Override
    public CalendarDagakVO getDagak(String userId, LocalDate today) {
        Calendar todayCalender = calendarRepository.findCalendarByCalendarDate(today);
        CalendarDagakVO todayCalendarDagakVO = new CalendarDagakVO();
        todayCalendarDagakVO.setUserId(todayCalender.getUserId());
        todayCalendarDagakVO.setDagakId(todayCalender.getDagakId());
        return todayCalendarDagakVO;
    }

    @Override
    public void updateGak(Integer dagakId, Integer gakId, Integer categoryId, Integer runningTime) {
        Gak gak = gakRepository.findById(gakId).orElseThrow(() -> new BaseException(NOT_EXIST_GAK));
        if (categoryId != null) {
            gak.setCategoryId(categoryId);
        }
        if (runningTime != null) {
            gak.setRunningTime(runningTime);
        }
        gakRepository.save(gak);
    }

    @Override
    public void deleteGak(Integer deleteGakId) {
        gakRepository.deleteById(deleteGakId);
    }

    @Override
    public void updateRemainGakOrder(List<GakDto> remainGaks) {
        for (GakDto remainGak : remainGaks) {
            Gak gak = gakRepository.findById(remainGak.getGakId()).orElseThrow(() -> new BaseException(NOT_EXIST_GAK));
            gak.setGakOrder(remainGak.getGakOrder());
            gakRepository.save(gak);
        }
    }

    @Override
    public void registerDagak(RegisterDagakDto registerDagakDto) {
        // 유효한 다각인지 확인
        if (!isExistDagakId(registerDagakDto.getDagakId()))
            throw new BaseException(NOT_EXIST_DAGAK);

//        log.info("registerDagakDto.getCalendarDate() : {}", registerDagakDto.getCalendarDate());
//        if(registerDagakDto.getCalendarDate() instanceof LocalDate){
//            log.info("weslhngfwielosghfwoilesjfolwisejflewjfdwlkefnqekjafrbnlwsr");
//        }
        Calendar calendarByCalendarDate = calendarRepository.findCalendarByCalendarDate(registerDagakDto.getCalendarDate());
//            Calendar byCalendarDateStartsWith = dagakMapper.getCalendarByCalendarDate(calendarDate.substring(0, 10));
        log.info("=========== byCalendarDateStartsWith : {}", calendarByCalendarDate);
        if(calendarByCalendarDate == null){
            // 해당 날에 등록된 다각이 없음
            calendarRepository.save(
                    Calendar.builder()
                            .dagakId(registerDagakDto.getDagakId())
                            .userId(registerDagakDto.getUserId())
                            .calendarDate(registerDagakDto.getCalendarDate())
                            .build()
            );
        } else{
            // 해당 날에 등록된 다각이 있음
            calendarRepository.save(
                    Calendar.builder()
                            .calendarDagakId(calendarByCalendarDate.getCalendarDagakId())
                            .dagakId(registerDagakDto.getDagakId())
                            .userId(registerDagakDto.getUserId())
                            .calendarDate(registerDagakDto.getCalendarDate())
                            .build()
            );
        }

//        }

    }

    @Override
    public boolean isExistDagakId(Integer dagakId) {
        Optional<Dagak> byDagakId = dagakRepository.findById(dagakId);
        if (byDagakId.isPresent())
            return true;
        return false;
    }


}
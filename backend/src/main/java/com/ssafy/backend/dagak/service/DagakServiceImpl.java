package com.ssafy.backend.dagak.service;

import com.ssafy.backend.category.model.repository.CategoryRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.dagak.model.domain.Calendar;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.domain.GakHistory;
import com.ssafy.backend.dagak.model.dto.DagakDTO;
import com.ssafy.backend.dagak.model.dto.GakDTO;
import com.ssafy.backend.dagak.model.dto.AddDagakDateDTO;
import com.ssafy.backend.dagak.model.dto.UpdateMemoryTimeDTO;
import com.ssafy.backend.dagak.model.repository.CalendarRepository;
import com.ssafy.backend.dagak.model.repository.DagakRepository;
import com.ssafy.backend.dagak.model.repository.GakHistoryRepository;
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
    GakHistoryRepository gakHistoryRepository;

    @Override
    public int addDagak(DagakDTO dagakDTO) {
        return dagakRepository.save(
                Dagak.builder()
                        .userId(dagakDTO.getUserId())
                        .totalTime(dagakDTO.getTotalTime())
                        .build()
        ).getDagakId();
    }

    @Override
    public void createGak(List<GakDTO> gaks) {
        //log.info("gak : {}", gaks);
        List<Gak> gakEntities = new ArrayList<>();
        for (GakDTO gak : gaks) {
            Gak build = Gak.builder()
                    .userId(gak.getUserId())
                    .gakOrder(gak.getGakOrder())
                    .categoryId(gak.getCategoryId())
                    .runningTime(gak.getRunningTime())
                    .dagakId(gak.getDagakId())
                    .build();
            gakEntities.add(build);
        }

        gakRepository.saveAll(gakEntities);
    }

    @Override
    public List<CalendarDagakVO> getCalendarList(String userId) {
        List<CalendarDagakVO> result = new ArrayList<>();

        List<Calendar> byUserId = calendarRepository.findByUserId(userId);
        for (Calendar calendar : byUserId) {
            CalendarDagakVO calendarDagakVO = new CalendarDagakVO(calendar.getCalendarDagakId(), calendar.getDagakId(), calendar.getCalendarDate());
            result.add(calendarDagakVO);
        }
        return result;
    }

    @Override
    public List<CalendarDagakVO> getCalendarGaks(List<CalendarDagakVO> calendarDagaks) {

        for (CalendarDagakVO calendarDagakVO : calendarDagaks) {
            List<Gak> allByDagakId = gakRepository.findAllByDagakId(calendarDagakVO.getDagakId());
            calendarDagakVO.setGaks(allByDagakId);
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
        Calendar todayCalender = calendarRepository.findCalendarByCalendarDateAndAndUserId(today, userId);
        CalendarDagakVO todayCalendarDagakVO = new CalendarDagakVO();
        todayCalendarDagakVO.setUserId(todayCalender.getUserId());
        todayCalendarDagakVO.setDagakId(todayCalender.getDagakId());
        return todayCalendarDagakVO;
    }

    @Override
    public void modifyGak(Integer dagakId, Integer gakId, Integer categoryId, Integer runningTime) {
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
    public void modifyGakOrder(List<GakDTO> Gaks) {
        for (GakDTO Gak : Gaks) {
            Gak gak = gakRepository.findById(Gak.getGakId()).orElseThrow(() -> new BaseException(NOT_EXIST_GAK));
            gak.setGakOrder(Gak.getGakOrder());
            gakRepository.save(gak);
        }
    }

    @Override
    public void addDagakDate(AddDagakDateDTO addDagakDateDto) {
        // 유효한 다각인지 확인
        if (!isExistDagakId(addDagakDateDto.getDagakId()))
            throw new BaseException(NOT_EXIST_DAGAK);

        Calendar calendarByCalendarDate = calendarRepository.findCalendarByCalendarDate(addDagakDateDto.getCalendarDate());
        log.info("=========== byCalendarDateStartsWith : {}", calendarByCalendarDate);
        if (calendarByCalendarDate == null) {
            // 해당 날에 등록된 다각이 없음
            calendarRepository.save(
                    Calendar.builder()
                            .dagakId(addDagakDateDto.getDagakId())
                            .userId(addDagakDateDto.getUserId())
                            .calendarDate(addDagakDateDto.getCalendarDate())
                            .build()
            );
        } else {
            // 해당 날에 등록된 다각이 있음
            calendarRepository.save(
                    Calendar.builder()
                            .calendarDagakId(calendarByCalendarDate.getCalendarDagakId())
                            .dagakId(addDagakDateDto.getDagakId())
                            .userId(addDagakDateDto.getUserId())
                            .calendarDate(addDagakDateDto.getCalendarDate())
                            .build()
            );
        }

    }

    @Override
    public boolean isExistDagakId(Integer dagakId) {
        Optional<Dagak> byDagakId = dagakRepository.findById(dagakId);
        if (byDagakId.isPresent())
            return true;
        return false;
    }

    @Override
    public void deleteDagak(Integer deleteDagakId) {
        dagakRepository.deleteById(deleteDagakId);
        List<Calendar> dagaksOfCalendar = calendarRepository.findAllByDagakId(deleteDagakId);
        boolean isAfter;
        for (Calendar dagakOfCalendar : dagaksOfCalendar) {
            isAfter = dagakOfCalendar.getCalendarDate().isAfter(LocalDate.now());
            if (isAfter) {
                calendarRepository.deleteById(dagakOfCalendar.getCalendarDagakId());
            }
        }
    }

    @Override
    public void modifyMemoryTime(UpdateMemoryTimeDTO updateMemoryTimeDto) {
        LocalDate today = LocalDate.now();

        GakHistory gakHistory = gakHistoryRepository.findByGakIdAndCreatedDate(updateMemoryTimeDto.getGakId(), today);

        if (gakHistory == null) {
            // 기록이 없는 각
            gakHistoryRepository.save(
                    GakHistory.builder()
                            .memoryTime(updateMemoryTimeDto.getMemoryTime())
                            .createdDate(today)
                            .categoryId(updateMemoryTimeDto.getCategoryId())
                            .calendarId(updateMemoryTimeDto.getCalendarId())
                            .userId(updateMemoryTimeDto.getUserId())
                            .build()
            );
        } else {
            // 기록이 있는 각
            int totalMemoryTime = gakHistory.getMemoryTime() + updateMemoryTimeDto.getMemoryTime();
            gakHistory.setMemoryTime(totalMemoryTime);
            gakHistoryRepository.save(gakHistory);
        }

    }


}
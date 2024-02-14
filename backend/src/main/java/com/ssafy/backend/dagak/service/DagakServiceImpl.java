package com.ssafy.backend.dagak.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.repository.CategoryRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.dagak.model.domain.Calendar;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.domain.GakHistory;
import com.ssafy.backend.dagak.model.dto.*;
import com.ssafy.backend.dagak.model.repository.CalendarRepository;
import com.ssafy.backend.dagak.model.repository.DagakRepository;
import com.ssafy.backend.dagak.model.repository.GakHistoryRepository;
import com.ssafy.backend.dagak.model.repository.GakRepository;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import com.ssafy.backend.dagak.model.vo.TodayGakVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

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
                        .dagakName(dagakDTO.getDagakName())
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
        Calendar todayCalender = calendarRepository.findCalendarByCalendarDateAndUserId(today, userId)
                .orElseThrow(() ->
                        new BaseException(NOT_FOUND_TODAY_DAGAK));
        CalendarDagakVO todayCalendarDagakVO = new CalendarDagakVO();
        todayCalendarDagakVO.setCalendarDagakId(todayCalender.getCalendarDagakId());
        todayCalendarDagakVO.setUserId(todayCalender.getUserId());
        todayCalendarDagakVO.setDagakId(todayCalender.getDagakId());
        todayCalendarDagakVO.setGaks(gakRepository.findAllByDagakId(todayCalender.getDagakId()));
        todayCalendarDagakVO.setTotalTime(dagakRepository.findByDagakId(todayCalender.getDagakId()).getTotalTime());

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
                            .gakId(updateMemoryTimeDto.getGakId())
                            .updatedDate(today)
                            .build()
            );
        } else {
            // 기록이 있는 각
            int totalMemoryTime = gakHistory.getMemoryTime() + updateMemoryTimeDto.getMemoryTime();
            gakHistory.setMemoryTime(totalMemoryTime);
            gakHistoryRepository.save(gakHistory);
        }

    }

    @Override
    public List<GakHistory> getGaksOfHistory(String userId, LocalDate today) {
        return gakHistoryRepository.findAllByUserIdAndCreatedDate(userId, today);
    }

    @Override
    public TodayGakVO enterRoomGetGakToStudy(String userId) {
        TodayGakVO todayGakVO = new TodayGakVO();
        LocalDate today = LocalDate.now();
        CalendarDagakVO todayDagakVO = getDagak(userId, today);
        Integer calendarId = todayDagakVO.getCalendarDagakId();
        List<Gak> todayGaks = todayDagakVO.getGaks();
        List<GakHistory> historyGaks = getGaksOfHistory(userId, today);

        log.info("historyGaks : {}", historyGaks);
        System.out.println("님아 왜그러세요 제발");
        System.out.println(todayGaks + " " + todayGaks.size());
        System.out.println(historyGaks + " " + historyGaks.size());

        if (historyGaks.isEmpty()) {  // 공부 아예 처음 시작.
            Gak todayGak = todayGaks.get(0);
            todayGakVO.setUserId(userId);
            todayGakVO.setCalendarId(calendarId);
            todayGakVO.setGakId(todayGak.getGakId());
            todayGakVO.setTotalTime(todayGak.getRunningTime());
            todayGakVO.setCategoryId(todayGak.getCategoryId());
            todayGakVO.setMemoryTime(0);
            todayGakVO.setGakOrder(1);
            todayGakVO.setRequiredStudyTime(todayGak.getRunningTime());

            Category category = categoryRepository.findById(todayGak.getCategoryId()).orElseThrow(() -> new BaseException(FAIL_TO_CONNECT));
            todayGakVO.setCategoryName(category.getCategoryName());

        } else if (todayGaks.size() >= historyGaks.size()) { // 루틴 수행 도중일 때.
            Gak todayGak = null;
            int nowStudyingTime = 0;
            for (int i = 0; i < historyGaks.size(); i++){
                if (i == historyGaks.size()-1){
                    if (historyGaks.get(i).getMemoryTime() >= todayGaks.get(i).getRunningTime()) { // 기존 시간보다 많이 한 경우
                        nowStudyingTime += historyGaks.get(i).getMemoryTime();
                        if (todayGaks.size() == historyGaks.size()){ // 마지막 루틴일 때
                            todayGak = todayGaks.get(historyGaks.size() - 1);
                            todayGakVO.setRequiredStudyTime(0);
                        } else {  // 다음 루틴으로 넘어가야 할 때
                            todayGak = todayGaks.get(historyGaks.size());
                            todayGakVO.setRequiredStudyTime(todayGak.getRunningTime());
                        }
                    } else {   // 아직 수행중일 경우
                        todayGak = todayGaks.get(i);
                        nowStudyingTime += historyGaks.get(i).getMemoryTime();
                        todayGakVO.setRequiredStudyTime(todayGak.getRunningTime()-historyGaks.get(i).getMemoryTime());
                    }
                } else {
                    nowStudyingTime += historyGaks.get(i).getMemoryTime();
                }

            }
            todayGakVO.setUserId(userId);
            todayGakVO.setCalendarId(calendarId);
            todayGakVO.setGakId(todayGak.getGakId());
            todayGakVO.setCategoryId(todayGak.getCategoryId());
            todayGakVO.setTotalTime(todayDagakVO.getTotalTime());
            todayGakVO.setMemoryTime(nowStudyingTime);
            todayGakVO.setGakOrder(historyGaks.size());

            Category category = categoryRepository.findById(todayGak.getCategoryId()).orElseThrow(() -> new BaseException(FAIL_TO_CONNECT));
            todayGakVO.setCategoryName(category.getCategoryName());
        }
        System.out.println(todayGakVO);
        return todayGakVO;
    }


    @Override
    @Transactional
    public void deleteCalendarDagak(DeleteCalendarDagakDTO deleteCalendarDagakDTO) {
        try {
            calendarRepository.deleteByCalendarDagakIdAndUserId(deleteCalendarDagakDTO.getCalendarDagakId(), deleteCalendarDagakDTO.getUserId());
        } catch (Exception e) {
            throw new BaseException(NOT_FOUND_CALENDAR_DAGAK);
        }
    }

    @Override
    public List<CalendarDagakVO> getDagakName(List<CalendarDagakVO> calendarDagakList) {
        for (CalendarDagakVO calendarDagakVO : calendarDagakList) {
            Dagak byDagakId = dagakRepository.findByDagakId(calendarDagakVO.getDagakId());
            String dagakName = byDagakId.getDagakName();
            calendarDagakVO.setDagakName(dagakName);
        }

        return calendarDagakList;
    }


}
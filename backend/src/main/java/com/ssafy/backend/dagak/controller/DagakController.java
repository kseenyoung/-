package com.ssafy.backend.dagak.controller;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.domain.GakHistory;
import com.ssafy.backend.dagak.model.dto.DagakDTO;
import com.ssafy.backend.dagak.model.dto.GakDTO;
import com.ssafy.backend.dagak.model.dto.AddDagakDateDTO;
import com.ssafy.backend.dagak.model.dto.UpdateMemoryTimeDTO;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import com.ssafy.backend.dagak.service.DagakFacade;
import com.ssafy.backend.dagak.service.DagakService;
import com.ssafy.backend.user.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("dagak")
@Slf4j
public class DagakController {

    @Autowired
    private DagakFacade dagakFacade;

    @Autowired
    private DagakService dagakService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public BaseResponse<?> dagak(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        String sign = (String) body.get("sign");
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        if (session == null) {
            return new BaseResponse<>(EMPTY_SESSION);
        }

        if (sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign) {
            case "addDagak":
                List<Map<String, String>> json = (List<Map<String, String>>) body.get("gaks");
                List<GakDTO> gaks = new ArrayList<>();

                int order = 1, totalTime = 0;

                for (Map<String, String> gak : json) {
                    String runningTime = gak.get("runningTime");
                    String category = gak.get("category");
                    GakDTO gakDTO = new GakDTO(category, order++, runningTime, userId);

                    totalTime += gakDTO.getRunningTime();
                    gaks.add(gakDTO);
                }

                // 다각 생성
                DagakDTO dagakDTO = new DagakDTO(userId, totalTime);
                dagakFacade.addDagak(dagakDTO, gaks);

                return new BaseResponse<>(SUCCESS);

            case "addDagakDate":
                String registDagakId = (String) body.get("dagakId");
                List<List<Integer>> calendarDates = (List<List<Integer>>) body.get("calendarDate");

                for (List<Integer> calendarDate : calendarDates) {
                    AddDagakDateDTO addDagakDateDto = new AddDagakDateDTO(userId, registDagakId, LocalDate.of(calendarDate.get(0), calendarDate.get(1), calendarDate.get(2)));

                    dagakService.addDagakDate(addDagakDateDto);
                }

                return new BaseResponse<>(SUCCESS);

            /*
             * [POST] 다각 삭제하기
             * 그와 관련된 모든 정보도 삭제됩니다.
             * Dagak과 gak 테이블의 데이터, Calendar는 현재 날짜 이후 데이터,
             * gak_history 의 데이터는 삭제되지 않습니다.
             */
            case "deleteDagak":
                Integer deleteDagakId = (Integer) body.get("deleteDagakId");
                dagakFacade.deleteDagak(deleteDagakId);
                return new BaseResponse<>(SUCCESS);

            case "modifyMemoryTime":
                String gakId = (String) body.get("gakId");
                Integer memoryTime = (Integer) body.get("memoryTime");
                String categoryId = (String) body.get("categoryId");
                String calendarId = (String) body.get("calendarId");
                UpdateMemoryTimeDTO updateStartTimeDto = new UpdateMemoryTimeDTO(gakId, categoryId, calendarId, memoryTime, userId);

                dagakService.modifyMemoryTime(updateStartTimeDto);

                return new BaseResponse<>(SUCCESS);
            /*
             * [POST] 다각의 상세정보들 수정
             */
            case "modifyGak":
                Integer updateDagakId = (Integer) body.get("dagakId");
                Integer updateGakId = (Integer) body.get("gakId");
                Integer updateCategoryId = (Integer) body.get("categoryId");
                Integer updateRunningTime = (Integer) body.get("runningTime");
                if (updateCategoryId == null && updateRunningTime == null) {
                    throw new BaseException(DATA_NOT_CHANGED);
                }
                dagakService.modifyGak(updateDagakId, updateGakId, updateCategoryId, updateRunningTime);
                return new BaseResponse<>(SUCCESS);

            /*
             * [POST] 각 삭제하기
             * 각 삭제 후, 남은 각의 순서를 다시 업데이트
             */
            case "deleteGak":
                Integer deleteGakId = (Integer) body.get("gakId");
                List<Map<String, Integer>> remainGakInformation = (List<Map<String, Integer>>) body.get("remainGakInformation");
                dagakFacade.deleteGak(deleteGakId, remainGakInformation);
                return new BaseResponse<>(SUCCESS);

            /*
             * [POST] 다각 순서 업데이트
             */
            case "modifyGakOrder":
                List<Map<String, Integer>> GakInformation = (List<Map<String, Integer>>) body.get("GakInformation");
                List<GakDTO> updateOrderGaks = new ArrayList<>();
                if (!GakInformation.isEmpty()) {
                    for (Map<String, Integer> gak : GakInformation) {
                        Integer updateOrderGakId = gak.get("gakId");
                        Integer updateOrder = gak.get("gakOrder");
                        updateOrderGaks.add(new GakDTO(updateOrderGakId, updateOrder));
                    }
                    dagakService.modifyGakOrder(updateOrderGaks);
                }
                return new BaseResponse<>(SUCCESS);
        }

        return new BaseResponse(NOT_MATCH_SIGN);
    }

    @GetMapping("getAllCalendarList")
    public BaseResponse<?> getAllCalendarList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("User");
        if (session == null)
            return new BaseResponse<>(EMPTY_SESSION);

        String userId = user.getUserId();

        List<CalendarDagakVO> calendarDagakVOS = dagakFacade.getAllCalendarList(userId);

        return new BaseResponse<>(calendarDagakVOS);
    }

    /*
     * [GET] 사용자의 모든 다각 반환
     */
    @GetMapping("getAllDagakList")
    public BaseResponse<?> getAllDagakList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("User");
        if (session == null)
            return new BaseResponse<>(EMPTY_SESSION);

        String userId = user.getUserId();
        List<Dagak> dagakList = dagakService.getDagakList(userId);
        return new BaseResponse<>(dagakList);
    }

    /*
     * [GET] 사용자의 다각의 상세 정보 반환
     */
    @GetMapping("getAllGakList")
    public BaseResponse<?> getAllGakList(@RequestParam Integer dagakId) {
        List<Gak> dagakInformation = dagakService.getGakInformation(dagakId);
        return new BaseResponse<>(dagakInformation);
    }

    /*
     * [GET] 오늘의 다각 정보 반환
     */
    @GetMapping("getTodayDagak")
    public BaseResponse<?> getTodayDagak(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("User");
        if (session == null)
            return new BaseResponse<>(EMPTY_SESSION);

        String userId = user.getUserId();
        LocalDate today = LocalDate.now();
        CalendarDagakVO todayDagakVO = dagakService.getDagak(userId, today);
        return new BaseResponse<>(todayDagakVO);
    }

    @GetMapping("getCategoryList")
    public BaseResponse<?> getCategoryList() {

        List<Category> categories = categoryService.getAllCategoryList();

        return new BaseResponse<>(categories);
    }

    @GetMapping("getGakToStudy")
    public BaseResponse<?> getGakToStudy(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        if (session == null)
            return new BaseResponse<>(EMPTY_SESSION);
        String userId = user.getUserId();
        LocalDate today = LocalDate.now();
        CalendarDagakVO todayDagakVO = dagakService.getDagak(userId, today);

        List<Gak> todayGaks = todayDagakVO.getGaks();
        List<GakHistory> historyGaks = dagakService.getGaksOfHistory(userId, today);

        System.out.println(todayGaks);
        System.out.println(historyGaks);

        for (GakHistory historyGak: historyGaks) {
            
        }

        return new BaseResponse<>(todayDagakVO);
    }
}

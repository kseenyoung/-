package com.ssafy.backend.dagak.controller;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.dto.RegisterDagakDto;
import com.ssafy.backend.dagak.model.dto.UpdateMemoryTimeDto;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import com.ssafy.backend.dagak.service.DagakFacade;
import com.ssafy.backend.dagak.service.DagakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<?> dagak(@RequestBody Map<String, Object> body, HttpServletRequest request){
        String sign = (String) body.get("sign");
//        HttpSession session = request.getSession(false);
//
//        User user = (User) session.getAttribute("User");
//        if(session == null)
//            return new BaseResponse<>(EMPTY_SESSION);

//        String userId = user.getUserId();
        String userId = "ssafy";

        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "createDagak":
                List<Map<String, String>> json = (List<Map<String, String>>) body.get("gaks");
                List<GakDto> gaks = new ArrayList<>();

                int order = 1, totalTime = 0;

                for(Map<String, String> gak: json){
                    String runningTime = gak.get("runningTime");
                    String category = gak.get("category");
                    GakDto gakDto = new GakDto(category, order++, runningTime, userId);

                    totalTime += gakDto.getRunningTime();
                    gaks.add(gakDto);
                }

                // 다각 생성
                DagakDto dagakDto = new DagakDto(userId, totalTime);
                dagakFacade.createDagak(dagakDto, gaks);

                return new BaseResponse<>(SUCCESS);

            case "registerDagak":
                String registerDagakId = (String) body.get("dagakId");
                List<List<Integer>> calendarDates = ( List<List<Integer>>) body.get("calendarDate");

                for(List<Integer> calendarDate : calendarDates){
                    RegisterDagakDto registerDagakDto = new RegisterDagakDto(userId, registerDagakId, LocalDate.of(calendarDate.get(0),calendarDate.get(1),calendarDate.get(2)));

                    dagakService.registerDagak(registerDagakDto);
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

            case "updateMemoryTime":
                String gakId = (String) body.get("gakId");
                Integer memoryTime = (Integer) body.get("memoryTime");
                String categoryId = (String) body.get("categoryId");
                String calendarId = (String) body.get("calendarId");
                UpdateMemoryTimeDto updateStartTimeDto = new UpdateMemoryTimeDto(gakId, categoryId, calendarId, memoryTime, userId);

                dagakService.updateMemoryTime(updateStartTimeDto);

                return new BaseResponse<>(SUCCESS);
            /*
             * [POST] 다각의 상세정보들 수정
             */
            case "updateGak":
                Integer updateDagakId = (Integer) body.get("dagakId");
                Integer updateGakId = (Integer) body.get("gakId");
                Integer updateCategoryId = (Integer) body.get("categoryId");
                Integer updateRunningTime = (Integer) body.get("runningTime");
                if (updateCategoryId==null && updateRunningTime==null){
                    throw new BaseException(DATA_NOT_CHANGED);
                }
                dagakService.updateGak(updateDagakId, updateGakId, updateCategoryId, updateRunningTime);
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
            case "updateGakOrder":
                List<Map<String, Integer>> GakInformation = (List<Map<String, Integer>>) body.get("GakInformation");
                List<GakDto> updateOrderGaks = new ArrayList<>();
                if (!GakInformation.isEmpty()){
                    for(Map<String, Integer> gak: GakInformation){
                        Integer updateOrderGakId = gak.get("gakId");
                        Integer updateOrder = gak.get("gakOrder");
                        updateOrderGaks.add(new GakDto(updateOrderGakId, updateOrder));
                    }
                    dagakService.updateGakOrder(updateOrderGaks);
                }
                return new BaseResponse<>(SUCCESS);
        }

        return new BaseResponse(NOT_MATCH_SIGN);
    }

    @GetMapping("calendar")
    public BaseResponse<?> getCalendarDagaks(HttpServletRequest request){
        //        HttpSession session = request.getSession(false);
//
//        User user = (User) session.getAttribute("User");
//        if(session == null)
//            return new BaseResponse<>(EMPTY_SESSION);

//        String userId = user.getUserId();
        String userId = "ssafy";

        List<CalendarDagakVO> calendarDagakVOS = dagakFacade.getCalendarDagaks(userId);
//        System.out.println("최최초치ㅗ치ㅗ치ㅗ치쵳 최종 " + calendarDagakVOS);

        return new BaseResponse<>(calendarDagakVOS);
    }

    /*
     * [GET] 사용자의 모든 다각 반환
     */
    @GetMapping("dagaks")
    public BaseResponse<?> getDagaks(@RequestParam String userId){
        List<Dagak> dagakList = dagakService.getDagakList(userId);
        return new BaseResponse<>(dagakList);
    }
    
    /*
     * [GET] 사용자의 다각의 상세 정보 반환
     */
    @GetMapping("gaks")
    public BaseResponse<?> getDagakInformation(@RequestParam Integer dagakId){
        List<Gak> dagakInformation = dagakService.getGakInformation(dagakId);
        return new BaseResponse<>(dagakInformation);
    }
    
    /*
     * [GET] 오늘의 다각 정보 반환
     */
    @GetMapping("today")
    public BaseResponse<?> getTodayDagak(@RequestParam String userId){
        LocalDate today = LocalDate.now();
        CalendarDagakVO todayDagakVO = dagakService.getDagak(userId, today);
        return new BaseResponse<>(todayDagakVO);
    }
    @GetMapping("categories")
    public BaseResponse<?> getCategories(){

        List<Category> categories = categoryService.getAllCategories();

        return new BaseResponse<>(categories);
    }


}

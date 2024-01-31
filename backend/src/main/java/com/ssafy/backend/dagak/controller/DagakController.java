package com.ssafy.backend.dagak.controller;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.vo.CalendarDagakVO;
import com.ssafy.backend.dagak.service.DagakFacade;
import com.ssafy.backend.dagak.service.DagakService;
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

                break;

            case "modifyDagak":
                break;

            case "deleteDagak":
                break;

            case "updateEndTime":
                break;

            /*
             * [POST] 다각의 상세정보들 수정
             * 바뀌어야 할 부분:
             * 다각 테이블의 총 시간 원래 시간이랑 비교해서 바꾸고...
             * category_id, setting_time
             */
            case "updateGak":
                Integer updateDagakId = (Integer) body.get("dagakId");
                Integer updateGakId = (Integer) body.get("gakId");
                Integer updateCategoryId = (Integer) body.get("categoryId");
                Integer updateRunningTime = (Integer) body.get("runningTime");
                Integer updateOrder = (Integer) body.get("updateOrder");
                if (updateCategoryId==null && updateRunningTime==null){
                    throw new BaseException(DATA_NOT_CHANGED);
                }
                dagakService.updateGak(updateDagakId, updateGakId, updateCategoryId, updateRunningTime);
                return new BaseResponse<>(SUCCESS);

            case "deleteGak":
                Integer deleteGakId = (Integer) body.get("gakId");
                dagakService.deleteGak(deleteGakId);

                List<Map<String, Integer>> remainGakInformation = (List<Map<String, Integer>>) body.get("remainGakInformation");
                List<GakDto> remainGaks = new ArrayList<>();
                if (!remainGakInformation.isEmpty()){
                    for(Map<String, Integer> gak: remainGakInformation){
                        Integer remainGakId = gak.get("gakId");
                        Integer remainOrder = gak.get("gakOrder");
                        remainGaks.add(new GakDto(remainGakId, remainOrder));
                    }
                    dagakService.updateRemainGakOrder(remainGaks);
                }


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

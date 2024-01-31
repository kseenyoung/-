package com.ssafy.backend.dagak.controller;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.service.CategoryService;
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

            case "getCalendarAll":
                break;

            case "getGaks":
                break;

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
     * request :
     */
    @GetMapping("dagaks")
    public BaseResponse<?> getDagaks(@RequestParam String userId){
        List<Dagak> dagakList = dagakService.getDagakList(userId);
        return new BaseResponse<>(dagakList);
    }

    @GetMapping("gaks")
    public BaseResponse<?> getDagakInformation(@RequestParam Integer dagakId){
        List<Gak> dagakInformation = dagakService.getGakInformation(dagakId);
        return new BaseResponse<>(dagakInformation);
    }

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

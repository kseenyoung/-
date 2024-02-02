package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.mokkoji.model.vo.MokkojiDetailVO;
import com.ssafy.backend.mokkoji.model.vo.MokkojiListVO;
import com.ssafy.backend.mokkoji.model.vo.MokkojiRankingsVO;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/mokkoji")
@RequiredArgsConstructor
public class MokkojiController {
    private final MokkojiFacade mokkojiFacade;

    //모꼬지 상세조회
    @GetMapping("/detail/{mokkojiId:[\\d]+}")
    public BaseResponse<?> detailData(
            @PathVariable(name = "mokkojiId") int mokkojiId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = new String("");
        if(session != null){
            User user = (User) session.getAttribute("User");
            userId = user.getUserId();
        }
        MokkojiDetailVO mokkojiDetailVO = mokkojiFacade.getDetailMokkoji(mokkojiId,userId);
        return new BaseResponse<>(mokkojiDetailVO);
    }


    //탑텐 조회
    @GetMapping("/rank10")
    public BaseResponse<?> mokkojiRankings(){
            List<MokkojiRankingsVO> mokkojiRankingsVO = mokkojiFacade.geTmokkojiTopTen();
        return new BaseResponse<>(mokkojiRankingsVO);
    }
    //이름으로 순위 검색
    @GetMapping("/rank/{mokkojiName}")
    public BaseResponse mokkojiRankings(@PathVariable(name = "") String mokkojiName){
        MokkojiRankingsVO mokkojiRankingsVO = mokkojiFacade.getByMokkojiNameRanking(mokkojiName);
        return new BaseResponse(mokkojiRankingsVO);
    }
    //모꼬지 리스트 조회
    @GetMapping("/list")
    public BaseResponse<?> getMokkojiList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword
            ,@RequestParam(value = "categories",required = false) List<Integer> categories){
        MokkojiListVO mokkojiListVO = mokkojiFacade.getMokkojiList(categories,page, keyword);
        return new BaseResponse<>(mokkojiListVO);
    }


    @PostMapping("")
    public BaseResponse<?> hideMokkojiURL(@RequestBody Map<String, Object> body
            , HttpServletRequest request) {
        //회원 체크
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BaseException(EMPTY_SESSION);
        }
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();
        String sign = (String) body.get("sign");

        switch (sign){
            case "deleteMokkoji":
                mokkojiFacade.deleteMokkoji(userId);
                return new BaseResponse<>(SUCCESS_DELETE_MOKKOJI);
            case "kickMember":
                String member = (String) body.get("member");
                if(member == null) throw new BaseException(NOT_EXIST_KICK_USER);
                mokkojiFacade.kickUser(userId,member);
                return new BaseResponse<>(SUCCESS_KICK_MOKKOJI_MEMBER);
            case "addMokkoji":
                String mokkojiName = (String) body.get("mokkojiName");
                String mokkjiStatus = (String) body.get("mokkojiStatus");
                List<Integer> categories = (List<Integer>) body.get("mokkojiCategories");
                MokkojiCreateRequestDTO DTO = MokkojiCreateRequestDTO.builder()
                        .mokkojiName(mokkojiName)
                        .mokkojiStatus(mokkjiStatus)
                        .mokkojiCategories(categories)
                        .leaderId(userId)
                        .build();
                mokkojiFacade.saveMokkoji(DTO);
                return new BaseResponse<>(SUCCESS);
            case "leaveMokkoji":
                mokkojiFacade.leaveMokkoji(userId);
                return new BaseResponse<>(SUCCESS);
            //모꼬지 가입 신청 나중에 ParseInt 수정해야됨
            case "requestMokkoji":
                String mokkojiId = (String) body.get("mokkojiId");
                mokkojiFacade.applyForMokkoji(
                        MokkojiApplyForRequestDTO.builder()
                                .userId(userId)
                                .mokkojiId(Integer.parseInt(mokkojiId))
                                .build()
                );
                return new BaseResponse<>(SUCCESS);
            case "accessMokkoji":
                String memberId = (String) body.get("memberId");
                mokkojiFacade.deleteAlarm(memberId,userId);
                mokkojiFacade.acceptForMokkoji(userId, memberId);
                return new BaseResponse<>(SUCCESS);
        }
        return new BaseResponse<>(NOT_MATCH_SIGN);
    }
}

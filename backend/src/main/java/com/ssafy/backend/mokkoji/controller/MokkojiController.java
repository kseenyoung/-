package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

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
            User user = (User) session.getAttribute("session");
            userId = user.getUserId();
        }
        MokkojiDetailResponseDto dto = mokkojiFacade.getDetailMokkoji(mokkojiId,userId);
        return new BaseResponse<>(dto);
    }


    //탑텐 조회
    @GetMapping("/rank10")
    public BaseResponse<?> mokkojiRankings(){
        List<MokkojiRankingsResponseDto> mokkojiRankingsResponseDto = mokkojiFacade.geTmokkojiTopTen();
        return new BaseResponse<>(mokkojiRankingsResponseDto);
    }
    //이름으로 순위 검색
    @GetMapping("/rank/{mokkojiName}")
    public BaseResponse mokkojiRankings(@PathVariable(name = "") String mokkojiName){
        MokkojiRankingsResponseDto mokkojiRankingsResponseDto = mokkojiFacade.getByMokkojiNameRanking(mokkojiName);
        return new BaseResponse(mokkojiRankingsResponseDto);
    }
    //모꼬지 리스트 조회
    @GetMapping("/list")
    public BaseResponse<?> getMokkojiList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword
            ,@RequestParam(value = "categories",required = false) List<Integer> categories){
        MokkojiListResponseDto mokkojiList = mokkojiFacade.getMokkojiList(categories,page, keyword);
        return new BaseResponse<>(mokkojiList);
    }


    @PostMapping("")
    public BaseResponse<?> hideMokkojiURL(@RequestBody HashMap<String, Object> body
            , HttpServletRequest request) {
        //회원 체크
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BaseException(EMPTY_SESSION);
        }
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();
//        sign 체크
        String sign = (String) body.get("sign");
        // 모꼬지 삭제
        if ("deleteMokkoji".equals(sign)) {
            mokkojiFacade.deleteMokkoji(userId);
            return new BaseResponse<>(SUCCESS_DELETE_MOKKOJI);
        }
        //모꼬지 강퇴
        else if("kickMember".equals(sign)){
            String member = (String) body.get("member");
            if(member == null) throw new BaseException(NOT_EXIST_KICK_USER);
            mokkojiFacade.kickUser(userId,member);
            return new BaseResponse<>(SUCCESS_KICK_MOKKOJI_MEMBER);
        }
        //모꼬지 만들기
        else if ("addMokkoji".equals(sign)) {
            String mokkojiName = (String) body.get("mokkojiName");
            String mokkjiStatus = (String) body.get("mokkojiStatus");
            List<Integer> categories = (List<Integer>) body.get("mokkojiCategories");
            MokkojiCreateRequestDto dto = MokkojiCreateRequestDto.builder()
                    .mokkojiName(mokkojiName)
                    .mokkojiStatus(mokkjiStatus)
                    .mokkojiCategories(categories)
                    .leaderId(userId)
                    .build();

            mokkojiFacade.saveMokkoji(dto);
            return new BaseResponse<>(SUCCESS_CREATE_MOKKOJI);
        }
        //모꼬지 나가기
        else if ("leaveMokkoji".equals(sign)) {
            mokkojiFacade.leaveMokkoji(userId);
            return new BaseResponse<>(SUCCESS_LEAVE_MOKKOJI);
        }
        //모꼬지 가입 신청 나중에 ParseInt 수정해야됨
        else if ("ApplyMokkoji".equals(sign)) {
            String mokkojiId = (String) body.get("mokkojiId");
            mokkojiFacade.applyForMokkoji(
                    MokkojiApplyForRequestDto.builder()
                            .userId(userId)
                            .mokkojiId(Integer.parseInt(mokkojiId))
                            .build()
            );
            return new BaseResponse<>(SUCCESS_APPLY_FOR_MOKKOJI);
        }
        //모꼬지 가입 승인
        else if ("AcceptMokkoji".equals(sign)) {
            String memberId = (String) body.get("memberId");
            mokkojiFacade.deleteAlarm(userId,memberId);
            mokkojiFacade.acceptForMokkoji(userId, memberId);
            return new BaseResponse<>(SUCCESS_ACCEPT_MOKKOJI);
        }

        return new BaseResponse<>(EMPTY_SIGN);
    }
}

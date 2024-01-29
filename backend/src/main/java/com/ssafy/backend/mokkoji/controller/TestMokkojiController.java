package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/mokkojiTest")
@RequiredArgsConstructor
public class TestMokkojiController {
    private final MokkojiFacade mokkojiFacade;
    @GetMapping("/rank10")
    public BaseResponse<?> mokkojiRankings(){
        List<MokkojiRankingsResponseDto> mokkojiRankingsResponseDto = mokkojiFacade.geTmokkojiTopTen();
        return new BaseResponse<>(mokkojiRankingsResponseDto);
    }

    @GetMapping("/rank/{mokkojiName}")
    public BaseResponse<?> mokkojiRankings(@PathVariable(name = "") String mokkojiName){
        MokkojiRankingsResponseDto mokkojiRankingsResponseDto = mokkojiFacade.getByMokkojiNameRanking(mokkojiName);
        return new BaseResponse<>(mokkojiRankingsResponseDto);
    }

    @PostMapping("/create")
    public BaseResponse<?> createMokkoji(@RequestBody MokkojiCreateRequestDto dto){
        mokkojiFacade.saveMokkoji(dto);
        return new BaseResponse<>(SUCCESS_CREATE_MOKKOJI);
    }

    //모꼬지 조회
    @GetMapping("/list")
    public BaseResponse<?> getMokkojiList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword
            ,@RequestParam(value = "categories",required = false) List<Integer> categories){
        MokkojiListResponseDto mokkojiList = mokkojiFacade.getMokkojiList(categories,page, keyword);
        return new BaseResponse<>(mokkojiList);
    }
    //모꼬지 삭제
    @PostMapping("/delete")
    public BaseResponse<?> deleteMokkoji(@RequestBody String userId){
        mokkojiFacade.deleteMokkoji(userId);
        return new BaseResponse<>(SUCCESS_DELETE_MOKKOJI);
    }

    @PostMapping("/kick")
    public BaseResponse<?> kickUser(@RequestBody HashMap<String, Object> map) {
        String leader = (String)map.get("userId");
        String member = (String)map.get("userId1");
        if(leader  == null || member == null) throw new MyException("null 입력 하셨습니다", HttpStatus.BAD_REQUEST);
        mokkojiFacade.kickUser(leader,member);
        return new BaseResponse<>(SUCCESS_KICK_MOKKOJI_MEMBER);

    }

    //모꼬지 탈퇴 -> 내가 탈퇴하겠다 !
    @PostMapping("/leave")
    public BaseResponse<?> leaveMokkoji(@RequestBody String userId) {
        mokkojiFacade.leaveMokkoji(userId);
        return new BaseResponse<>(SUCCESS_KICK_MOKKOJI_MEMBER);
    }

    @GetMapping("/detail/{mokkojiId:[\\d]+}")
    public BaseResponse<?> detailData(
            @PathVariable(name = "mokkojiId") int mokkojiId,
            @RequestParam String userId) {
        MokkojiDetailResponseDto dto = mokkojiFacade.getDetailMokkoji(mokkojiId,userId);
        return new BaseResponse<>(dto);
    }

    //모꼬지 가입 신청 -> 일단 테스트
    @PostMapping("/applyFor/mokkoji")
    public BaseResponse<?> applyForMokkoji(@RequestBody MokkojiApplyForRequestDto dto) {
        mokkojiFacade.applyForMokkoji(dto);
        return new BaseResponse<>(SUCCESS_APPLY_FOR_MOKKOJI);
    }

    //모꼬지 가입 승인 -> 일단 테스트
    @PostMapping("/acceptFor/mokkoji")
    public BaseResponse<?> acceptForMokkoji(@RequestBody HashMap<String, Object> body) {
        String leaderId = (String) body.get("leaderId");
        String memberId = (String) body.get("memberId");
        mokkojiFacade.deleteAlarm(leaderId,memberId);
        mokkojiFacade.acceptForMokkoji(leaderId, memberId);

        return new BaseResponse<>(SUCCESS_ACCEPT_MOKKOJI);
    }

}

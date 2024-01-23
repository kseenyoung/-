package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/mokkojiTest")
@RequiredArgsConstructor
public class TestMokkojiController {
    private final MokkojiFacade mokkojiFacade;
    @GetMapping("/rank10")
    public ResponseEntity<HttpResponseBody<?>> mokkojiRankings(){
        List<MokkojiRankingsResponseDto> mokkojiRankingsResponseDto = mokkojiFacade.geTmokkojiTopTen();
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiRankingsResponseDto), HttpStatus.OK);
    }

    @GetMapping("/rank/{mokkojiName}")
    public ResponseEntity<HttpResponseBody<?>> mokkojiRankings(@PathVariable(name = "") String mokkojiName){
        MokkojiRankingsResponseDto mokkojiRankingsResponseDto = mokkojiFacade.getByMokkojiNameRanking(mokkojiName);
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiRankingsResponseDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponseBody<?>> createMokkoji(@RequestBody MokkojiCreateRequestDto dto){
        mokkojiFacade.saveMokkoji(dto);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "성공했대요"), HttpStatus.OK);
    }

    //모꼬지 조회
    @GetMapping("/list")
    public ResponseEntity<HttpResponseBody<?>> getMokkojiList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword
            ,@RequestParam(value = "categories",required = false) List<Integer> categories){
        MokkojiListResponseDto mokkojiList = mokkojiFacade.getMokkojiList(categories,page, keyword);
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiList),HttpStatus.OK);
    }
    //모꼬지 삭제
    @PostMapping("/delete")
    public ResponseEntity<HttpResponseBody<?>> deleteMokkoji(@RequestBody String userId){
        mokkojiFacade.deleteMokkoji(userId);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 삭제완료"), HttpStatus.OK);
    }

    @PostMapping("/kick")
    public ResponseEntity<HttpResponseBody<?>> kickUser(@RequestBody HashMap<String, Object> map) {
        String leader = (String)map.get("userId");
        String member = (String)map.get("userId1");
        if(leader  == null || member == null) throw new MyException("null 입력 하셨습니다", HttpStatus.BAD_REQUEST);
        mokkojiFacade.kickUser(leader,member);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 유저 강퇴 완료"), HttpStatus.OK);

    }

    //모꼬지 탈퇴 -> 내가 탈퇴하겠다 !
    @PostMapping("/leave")
    public ResponseEntity<HttpResponseBody<?>> leaveMokkoji(@RequestBody String userId) {
        mokkojiFacade.leaveMokkoji(userId);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "회원이 모꼬지를 나갔습니다"), HttpStatus.OK);
    }

    @GetMapping("/detail/{mokkojiId:[\\d]+}")
    public ResponseEntity<HttpResponseBody<?>> detailData(
            @PathVariable(name = "mokkojiId") int mokkojiId,
            @RequestParam String userId) {
        MokkojiDetailResponseDto dto = mokkojiFacade.getDetailMokkoji(mokkojiId,userId);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", dto), HttpStatus.OK);
    }

    //모꼬지 가입 신청 -> 일단 테스트
    @PostMapping("/applyFor/mokkoji")
    public ResponseEntity<HttpResponseBody<?>> applyForMokkoji(@RequestBody MokkojiApplyForRequestDto dto) {
        mokkojiFacade.applyForMokkoji(dto);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "메세지 성공"), HttpStatus.OK);
    }

}

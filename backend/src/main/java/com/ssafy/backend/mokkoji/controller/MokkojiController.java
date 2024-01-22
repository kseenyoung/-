package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.MokkojiCreateRequestDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiDetailResponseDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiListResponseDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mokkoji")
@RequiredArgsConstructor
public class MokkojiController {
    private final MokkojiFacade mokkojiFacade;

    //모꼬지 상세조회
    @GetMapping("/detail/{mokkojiId:[\\d]+}")
    public ResponseEntity<HttpResponseBody<?>> detailData(
            @PathVariable(name = "mokkojiId") int mokkojiId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = new String("");
        if(session != null){
            User user = (User) session.getAttribute("session");
            userId = user.getUserId();
        }
        MokkojiDetailResponseDto dto = mokkojiFacade.getDetailMokkoji(mokkojiId,userId);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", dto), HttpStatus.OK);
    }


    //탑텐 조회
    @GetMapping("/rank10")
    public ResponseEntity<HttpResponseBody<?>> mokkojiRankings(){
        List<MokkojiRankingsResponseDto> mokkojiRankingsResponseDto = mokkojiFacade.geTmokkojiTopTen();
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiRankingsResponseDto), HttpStatus.OK);
    }
    //이름으로 순위 검색
    @GetMapping("/rank/{mokkojiName}")
    public ResponseEntity<HttpResponseBody<?>> mokkojiRankings(@PathVariable(name = "") String mokkojiName){
        MokkojiRankingsResponseDto mokkojiRankingsResponseDto = mokkojiFacade.getByMokkojiNameRanking(mokkojiName);
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiRankingsResponseDto), HttpStatus.OK);
    }
    //모꼬지 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<HttpResponseBody<?>> getMokkojiList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword
            ,@RequestParam(value = "categories",required = false) List<Integer> categories){
        MokkojiListResponseDto mokkojiList = mokkojiFacade.getMokkojiList(categories,page, keyword);
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiList),HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> hideMokkojiURL(@RequestBody HashMap<String, Object> body
            , HttpServletRequest request) {
        System.out.println(body);

        //회원 체크
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MyException("로그인해주세요", HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("session");
        String userId = user.getUserId();
//        sign 체크
        String sign = (String) body.get("sign");
        // 모꼬지 삭제
        if ("deleteMokkoji".equals(sign)) {
            mokkojiFacade.deleteMokkoji(userId);
            return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 삭제완료"), HttpStatus.OK);
        }
        //모꼬지 강퇴
        else if("kickMember".equals(sign)){
            String member = (String) body.get("member");
            if(member == null) throw new MyException("강퇴할 유저 값이 없습니다", HttpStatus.BAD_REQUEST);
            mokkojiFacade.kickUser(userId,member);
            return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 유저 강퇴 완료"), HttpStatus.OK);
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
            return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 생성 완료"), HttpStatus.OK);
        }
        //모꼬지 나가기
        else if ("leaveMokkoji".equals("sign")) {
            mokkojiFacade.leaveMokkoji(userId);
            return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 나가기 완료"), HttpStatus.OK);
        }

        throw new MyException("해당 기능을 처리하지 못했습니다", HttpStatus.BAD_REQUEST);
    }
}

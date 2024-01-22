package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.MokkojiListResponseDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import com.ssafy.backend.mokkoji.service.MokkojiService;
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
    public ResponseEntity<HttpResponseBody<?>> deleteMokkoji(@RequestBody HashMap<String, Object> body
            , HttpServletRequest request) {

        //회원 체크
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MyException("로그인해주세요", HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("session");
        String userId = user.getUserId();
        //sign 체크
        String sign = (String) body.get("sign");
        // 모꼬지 삭제
        if ("deleteMokkoji".equals(sign)) {
            mokkojiFacade.deleteMokkoji(userId);
            return new ResponseEntity<>(new HttpResponseBody<>("OK", "모꼬지 삭제완료"), HttpStatus.OK);
        }

        throw new MyException("해당 기능을 처리하지 못했습니다", HttpStatus.BAD_REQUEST);
    }
}

package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.MokkojiListResponseDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import com.ssafy.backend.mokkoji.service.MokkojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

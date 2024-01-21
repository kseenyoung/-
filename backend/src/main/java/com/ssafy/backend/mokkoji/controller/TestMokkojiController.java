package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.MokkojiCreateRequestDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiListResponseDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.service.MokkojiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

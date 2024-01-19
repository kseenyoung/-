package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.service.MokkojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mokkojiTest")
@RequiredArgsConstructor
public class TestMokkojiController {
    private final MokkojiService mokkojiService;
    @GetMapping("/rank10")
    public ResponseEntity<HttpResponseBody<?>> mokkojiRankings(){
        List<MokkojiRankingsResponseDto> mokkojiRankingsResponseDto = mokkojiService.geTmokkojiRnakings();
        return new ResponseEntity<>(new HttpResponseBody<>("OK",mokkojiRankingsResponseDto), HttpStatus.OK);
    }



}

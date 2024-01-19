package com.ssafy.backend.mokkoji.controller;

import com.ssafy.backend.mokkoji.service.MokkojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mokkoji")
@RequiredArgsConstructor
public class MokkojiController {
    private final MokkojiService mokkojiService;


}

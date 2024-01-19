package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;

import java.util.List;

public interface MokkojiService {

    void createMokkoji();

    List<MokkojiRankingsResponseDto> geTmokkojiRnakings();

    MokkojiRankingsResponseDto searchRanking(String mokkojiName);
}

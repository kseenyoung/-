package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;

import java.util.List;

public interface MokkojiRankingService {
    public List<MokkojiRankings>getRankingTopTen();
    public List<MokkojiRankings> getByMokkojiName(String mokkojiName);
}

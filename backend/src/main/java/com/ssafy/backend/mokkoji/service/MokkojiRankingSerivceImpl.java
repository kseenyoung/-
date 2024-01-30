package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.repository.MokkojiRankingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_FOUND_RANKING_MOKKOJI;

@RequiredArgsConstructor
@Service
public class MokkojiRankingSerivceImpl implements MokkojiRankingService {
    private final MokkojiRankingsRepository mokkojiRankingsRepository;
    public void sizeZeroThrows(List<MokkojiRankings> list) {
        if(list.size() == 0) throw new BaseException(NOT_FOUND_RANKING_MOKKOJI);
    }
    public List<MokkojiRankings> getByMokkojiName(String mokkojiName){
        List<MokkojiRankings> mokkojiranking = mokkojiRankingsRepository.getMokkojiRankListByName(mokkojiName);
        sizeZeroThrows(mokkojiranking);
        return mokkojiranking;
    }
    public List<MokkojiRankings>getRankingTopTen(){
        List<MokkojiRankings> rankList = mokkojiRankingsRepository.getMokkojiRankList();
        sizeZeroThrows(rankList);
        return rankList;
    }

}

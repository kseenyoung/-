package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.repository.MokkojiRankingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MokkojiRankingSerivceImpl implements MokkojiRankingService {
    private final MokkojiRankingsRepository mokkojiRankingsRepository;
    public void sizeZeroThrows(List<MokkojiRankings> list) {
        if(list.size() == 0) throw new MyException("해당 길드가 없습니다.", HttpStatus.BAD_REQUEST);
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

package com.ssafy.backend.user.service;

import com.ssafy.backend.user.model.domain.UserRank;
import com.ssafy.backend.user.model.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRankServiceImpl implements UserRankService{
    private final UserRankRepository userRankRepository;
    @Override
    public List<UserRank> getUserRanks() {
        Pageable topTen = PageRequest.of(0, 10);
        Page<UserRank> topTenUserRanks = userRankRepository.findTop10ByUserRankTotalTimeIsNotNull(topTen);
        return topTenUserRanks.getContent();
    }
}

package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.dto.CategoryDto;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.dto.MokkojiDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//각 서비스를 끌어다 쓰기 위해서 퍼사드 형태로 모듈 분리
//하나의 서비스에는 하나의 레포만 존재하도록 설정 -> 비지니스 로직 처리
@RequiredArgsConstructor
@Service
public class MokkojiFacade {
    private final CategoryService categoryService;
    private final MokkojiRankingService mokkojiRankingService;

    @Transactional
    public MokkojiRankingsResponseDto getByMokkojiNameRanking(String mokkojiName) {
        List<MokkojiRankings> byMokkojiName = mokkojiRankingService.getByMokkojiName(mokkojiName);
        MokkojiRankings mokkojiRankings = byMokkojiName.get(0);

        MokkojiDto mokkojiDto = new MokkojiDto(mokkojiRankings);
        List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());

        List<CategoryDto> categories = categoriesEntities
                .stream().map(CategoryDto::new)
                .collect(Collectors.toList());
        return new MokkojiRankingsResponseDto(categories, mokkojiDto);
    }

    @Transactional
    public List<MokkojiRankingsResponseDto> geTmokkojiTopTen() {
        List<MokkojiRankingsResponseDto> list = new ArrayList<>();
        List<MokkojiRankings> topTen = mokkojiRankingService.getRankingTopTen();

        for (MokkojiRankings mokkojiRankings: topTen) {
            MokkojiDto mokkojiDto = new MokkojiDto(mokkojiRankings);
            List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());
            List<CategoryDto> categories = categoriesEntities
                    .stream().map(CategoryDto::new)
                    .collect(Collectors.toList());
            list.add(new MokkojiRankingsResponseDto(categories, mokkojiDto));
        }
        return list;

    }


}
